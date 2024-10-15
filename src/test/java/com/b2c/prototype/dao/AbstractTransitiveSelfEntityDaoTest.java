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
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import com.tm.core.util.TransitiveSelfEnum;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.b2c.prototype.dao.ConfigureSessionFactoryTest.getSessionFactory;
import static com.b2c.prototype.dao.DataSourcePool.getHikariDataSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DBRider
public abstract class AbstractTransitiveSelfEntityDaoTest {

    protected static IThreadLocalSessionManager sessionManager;
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
        DataSource dataSource = getHikariDataSource();
        connectionHolder = dataSource::getConnection;
        executor = DataSetExecutorImpl.instance("executor-name", connectionHolder);

        sessionFactory = getSessionFactory();
        sessionManager = new ThreadLocalSessionManager(sessionFactory);
    }

    @BeforeEach
    public void setUp() {
        try {
            Field sessionFactoryField = AbstractTransitiveSelfEntityDao.class.getDeclaredField("sessionFactory");
            sessionFactoryField.setAccessible(true);
            sessionFactoryField.set(dao, sessionFactory);

            Field sessionManagerField = AbstractTransitiveSelfEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    void getTransitiveSelfEntityListWithClass_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);

        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        List<TransitiveSelfEntity> result = dao.getTransitiveSelfEntityList(clazz, parameter);

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
    void getTransitiveSelfEntityListWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        assertThrows(RuntimeException.class, () -> {
            dao.getTransitiveSelfEntityList(clazz, parameter);
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
    void updateEntityTree_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);

        Parameter parameter = new Parameter("id", 2L);

        dao.updateEntityTree(updateEntityDataSet.getEntity(), parameter);
        verifyExpectedData(updateEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void updateEntityTree_transactionFailure() {
        IThreadLocalSessionManager sessionManager = mock(IThreadLocalSessionManager.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractTransitiveSelfEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Parameter parameter = new Parameter("id", 1L);

        when(sessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).persist(updateEntityDataSet.getEntity());

        assertThrows(RuntimeException.class, () -> {
            dao.updateEntityTree(updateEntityDataSet.getEntity(), parameter);
        });
        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(sessionManager).closeSession();
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

        IThreadLocalSessionManager sessionManager = mock(IThreadLocalSessionManager.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractTransitiveSelfEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Parameter parameter = new Parameter("id", 1L);
        when(sessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).remove(testEntityDataSet.getEntity());

        assertThrows(RuntimeException.class, () -> {
            dao.deleteEntityTree(parameter);
        });

        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(sessionManager).closeSession();
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
    void getOptionalTransitiveSelfEntityWithClass_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);

        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        Optional<TransitiveSelfEntity> result = dao.getOptionalTransitiveSelfEntity(clazz, parameter);

        assertTrue(result.isPresent());
        TransitiveSelfEntity resultEntity = result.get();
        assertNotNull(resultEntity);
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
    void getOptionalTransitiveSelfEntityWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 2L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        assertThrows(RuntimeException.class, () -> {
            dao.getOptionalTransitiveSelfEntity(clazz, parameter);
        });
    }

    @Test
    void getOptionalTransitiveSelfEntity_NoResult() {
        Parameter parameter = new Parameter("id", 200L);

        Optional<TransitiveSelfEntity> result = dao.getOptionalTransitiveSelfEntity(parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getOptionalTransitiveSelfEntityWithClass_NoResult() {
        Parameter parameter = new Parameter("id", 100L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        Optional<TransitiveSelfEntity> result = dao.getOptionalTransitiveSelfEntity(clazz, parameter);

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
    void getTransitiveSelfEntityWithClass_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        TransitiveSelfEntity result = dao.getTransitiveSelfEntity(clazz, parameter);

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
    void getTransitiveSelfEntityWith_Failure() {
        Parameter parameter = new Parameter("id", 100L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        assertThrows(RuntimeException.class, () -> {
            dao.getTransitiveSelfEntity(clazz, parameter);
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
