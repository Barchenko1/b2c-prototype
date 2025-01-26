package com.b2c.prototype.dao;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.api.dataset.YamlDataSet;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import com.github.database.rider.junit5.api.DBRider;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.transitive.AbstractTransitiveSelfEntityDao;
import com.tm.core.dao.transitive.ITransitiveSelfEntityDao;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.util.TransitiveSelfEnum;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.b2c.prototype.dao.ConfigureSessionFactoryTest.getSessionFactory;
import static com.b2c.prototype.dao.DataSourcePool.getPostgresDataSource;
import static com.b2c.prototype.dao.DatabaseQueries.cleanDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DBRider
public abstract class AbstractTransitiveSelfEntityDaoTest {

    protected static SessionFactory sessionFactory;

    protected static IEntityIdentifierDao entityIdentifierDao;

    private static ConnectionHolder connectionHolder;
    private static DataSetExecutor executor;

    protected static ITransitiveSelfEntityDao dao;
    protected String emptyDataSet;
    protected EntityDataSet<? extends TransitiveSelfEntity> testEntityDataSet;
    protected EntityDataSet<? extends TransitiveSelfEntity> saveEntityDataSet;
    protected EntityDataSet<? extends TransitiveSelfEntity> updateEntityDataSet;
    protected EntityDataSet<? extends TransitiveSelfEntity> deleteEntityDataSet;

    public AbstractTransitiveSelfEntityDaoTest() {
        this.emptyDataSet = getEmptyDataSetPath();
        this.testEntityDataSet = getTestDataSet();
        this.saveEntityDataSet = getSaveDataSet();
        this.updateEntityDataSet = getUpdateDataSet();
        this.deleteEntityDataSet = getDeleteDataSet();
    }

    @BeforeAll
    public static void setUpAll() {
        DataSource dataSource = getPostgresDataSource();
        connectionHolder = dataSource::getConnection;
        executor = DataSetExecutorImpl.instance("executor-name", connectionHolder);

        sessionFactory = getSessionFactory();
    }

    @BeforeEach
    public void setUp() {
        try {
            Field entityIdentifierDaoField = AbstractTransitiveSelfEntityDao.class.getDeclaredField("entityIdentifierDao");
            entityIdentifierDaoField.setAccessible(true);
            entityIdentifierDaoField.set(dao, entityIdentifierDao);

            Field sessionFactoryField = AbstractTransitiveSelfEntityDao.class.getDeclaredField("sessionFactory");
            sessionFactoryField.setAccessible(true);
            sessionFactoryField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() {
        cleanDatabase(connectionHolder);
    }

    protected abstract String getEmptyDataSetPath();
    protected abstract EntityDataSet<? extends TransitiveSelfEntity> getTestDataSet();
    protected abstract EntityDataSet<? extends TransitiveSelfEntity> getSaveDataSet();
    protected abstract EntityDataSet<? extends TransitiveSelfEntity> getUpdateDataSet();
    protected abstract EntityDataSet<? extends TransitiveSelfEntity> getDeleteDataSet();

    private void loadDataSet(String dataSetPath) {
        try (Connection connection = connectionHolder.getConnection()) {
            IDatabaseConnection dbConnection = new DatabaseConnection(connection);
            InputStream inputStream = this.getClass().getResourceAsStream(dataSetPath);
            if (inputStream == null) {
                throw new RuntimeException("Dataset file not found: " + dataSetPath);
            }
            IDataSet dataSet = new YamlDataSet(inputStream);
            DatabaseOperation.CLEAN_INSERT.execute(dbConnection, dataSet);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load dataset: " + dataSetPath, e);
        }
    }

    private void verifyExpectedData(String dataSetPath) {
        DataSetConfig dataSetConfig = new DataSetConfig(dataSetPath);
        String[] expectedDataSets = new String[]{dataSetPath};

        try {
            executor.compareCurrentDataSetWith(dataSetConfig, expectedDataSets);
        } catch (DatabaseUnitException e) {
            throw new RuntimeException("Dataset comparison failed", e);
        }
    }

    @Test
    void getTransitiveSelfEntityList_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);

        Parameter parameter = new Parameter("id", 1L);

        List<TransitiveSelfEntity> result = dao.getTransitiveSelfEntityList(parameter);

        assertEquals(1, result.size());
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void getTransitiveSelfEntityList_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getTransitiveSelfEntityList(parameter);
        });
    }

    @Test
    void saveEntityTree_success() {
        loadDataSet(emptyDataSet);
        dao.saveEntityTree(saveEntityDataSet.getEntity());
        verifyExpectedData(saveEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void saveEntityTree_transactionFailure() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractTransitiveSelfEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).persist(saveEntityDataSet.getEntity());

        assertThrows(RuntimeException.class, () -> {
            dao.saveEntityTree(saveEntityDataSet.getEntity());
        });

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    void updateEntityTreeOldMain_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);

        Parameter parameter = new Parameter("id", 2L);

        dao.updateEntityTreeOldMain(updateEntityDataSet.getEntity(), parameter);
        verifyExpectedData(updateEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void updateEntityTreeOldMain_transactionFailure() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);
        NativeQuery<Object> nativeQuery = mock(NativeQuery.class);

        try {
            Field sessionManagerField = AbstractTransitiveSelfEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Parameter parameter = new Parameter("id", 2L);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createNativeQuery(anyString(), any(Class.class))).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(updateEntityDataSet.getEntity());
        doThrow(new RuntimeException()).when(session).merge(updateEntityDataSet.getEntity());

        assertThrows(RuntimeException.class, () -> {
            dao.updateEntityTreeOldMain(updateEntityDataSet.getEntity(), parameter);
        });
        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    void deleteParentEntityTree_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);

        dao.deleteEntityTree(parameter);
        verifyExpectedData(emptyDataSet);
    }

    @Test
    void deleteRootEntityTree_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 2L);

        dao.deleteEntityTree(parameter);
        verifyExpectedData(deleteEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void deleteChildEntityTree_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 3L);

        dao.deleteEntityTree(parameter);
        verifyExpectedData(deleteEntityDataSet.getDataSetPath()[1]);
    }

    @Test
    void deleteEntityTree_transactionFailure() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);
        NativeQuery<Object> nativeQuery = mock(NativeQuery.class);

        try {
            Field sessionManagerField = AbstractTransitiveSelfEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Parameter parameter = new Parameter("id", 1L);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createNativeQuery(anyString(), any(Class.class))).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(testEntityDataSet.getEntity());
        doThrow(new RuntimeException()).when(session).remove(testEntityDataSet.getEntity());

        assertThrows(RuntimeException.class, () -> {
            dao.deleteEntityTree(parameter);
        });

        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    void getOptionalTransitiveSelfEntity_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);

        Parameter parameter = new Parameter("id", 1L);

        Optional<TransitiveSelfEntity> result = dao.getOptionalTransitiveSelfEntity(parameter);

        assertTrue(result.isPresent());
        TransitiveSelfEntity resultEntity = result.get();
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void getOptionalTransitiveSelfEntity_Failure() {
        Parameter parameter = new Parameter("id1", 2L);

        assertThrows(RuntimeException.class, () -> {
            dao.getOptionalTransitiveSelfEntity(parameter);
        });
    }

    @Test
    void getOptionalTransitiveSelfEntity_NoResult() {
        Parameter parameter = new Parameter("id", 200L);

        Optional<TransitiveSelfEntity> result = dao.getOptionalTransitiveSelfEntity(parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getTransitiveSelfEntity_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 2L);

        TransitiveSelfEntity result = dao.getTransitiveSelfEntity(parameter);

        assertNotNull(result);
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void getTransitiveSelfEntity_Failure() {
        Parameter parameter = new Parameter("id", 100L);

        assertThrows(RuntimeException.class, () -> {
            dao.getTransitiveSelfEntity(parameter);
        });
    }

    @Test
    void getTransitiveSelfEntitiesTree_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);

        Map<TransitiveSelfEnum, List<TransitiveSelfEntity>> result = dao.getTransitiveSelfEntitiesTree();

        assertNotNull(result.get(TransitiveSelfEnum.PARENT).get(0).getRootField());
        assertNotNull(result.get(TransitiveSelfEnum.ROOT).get(0).getRootField());
        assertNotNull(result.get(TransitiveSelfEnum.CHILD).get(0).getRootField());
    }


}
