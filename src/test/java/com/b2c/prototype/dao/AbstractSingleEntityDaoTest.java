package com.b2c.prototype.dao;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.YamlDataSet;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.dbunit.database.DatabaseConnection;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import com.github.database.rider.junit5.api.DBRider;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.ISingleEntityDao;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
import java.util.Optional;

import static com.b2c.prototype.dao.ConfigureSessionFactoryTest.getSessionFactory;
import static com.b2c.prototype.dao.DataSourcePool.getHikariDataSource;
import static com.b2c.prototype.dao.DatabaseQueries.cleanDatabase;
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
public abstract class AbstractSingleEntityDaoTest {

    protected static IThreadLocalSessionManager sessionManager;
    protected static SessionFactory sessionFactory;

    protected static IEntityIdentifierDao entityIdentifierDao;
    private static ConnectionHolder connectionHolder;
    private static DataSetExecutor executor;

    protected static ISingleEntityDao dao;
    protected String emptyDataSet;
    protected EntityDataSet<?> testEntityDataSet;
    protected EntityDataSet<?> saveEntityDataSet;
    protected EntityDataSet<?> updateEntityDataSet;

    public AbstractSingleEntityDaoTest() {
        this.emptyDataSet = getEmptyDataSetPath();
        this.testEntityDataSet = getTestDataSet();
        this.saveEntityDataSet = getSaveDataSet();
        this.updateEntityDataSet = getUpdateDataSet();
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
            Field sessionFactoryField = AbstractSingleEntityDao.class.getDeclaredField("sessionFactory");
            sessionFactoryField.setAccessible(true);
            sessionFactoryField.set(dao, sessionFactory);

            Field sessionManagerField = AbstractSingleEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() {
        cleanDatabase(connectionHolder);
    }

    protected abstract String getEmptyDataSetPath();
    protected abstract EntityDataSet<?> getTestDataSet();
    protected abstract EntityDataSet<?> getSaveDataSet();
    protected abstract EntityDataSet<?> getUpdateDataSet();

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
    void getEntityList_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);

        List<Object> result = dao.getEntityList(parameter);

        assertEquals(1, result.size());
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void getEntityListWithClass_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        List<Object> result = dao.getEntityList(clazz, parameter);

        assertEquals(1, result.size());
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void getEntityList_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getEntityList(parameter);
        });
    }

    @Test
    void getEntityListWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        assertThrows(RuntimeException.class, () -> {
            dao.getEntityList(clazz, parameter);
        });
    }

    @Test
    public void saveEntity_success() {
        loadDataSet(emptyDataSet);
        dao.saveEntity(saveEntityDataSet.getEntity());
        verifyExpectedData(saveEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    public void saveEntity_transactionFailure() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        try {
            Field sessionManagerField = AbstractSingleEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        doThrow(new RuntimeException()).when(session).persist(saveEntityDataSet.getEntity());

        assertThrows(RuntimeException.class, () -> {
            dao.saveEntity(saveEntityDataSet.getEntity());
        });

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    public void updateEntity_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        dao.updateEntity(updateEntityDataSet.getEntity());
        verifyExpectedData(updateEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    public void updateEntity_transactionFailure() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        try {
            Field sessionManagerField = AbstractSingleEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        doThrow(new RuntimeException()).when(session).merge(updateEntityDataSet.getEntity());

        assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(updateEntityDataSet.getEntity());
        });

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    public void deleteEntity_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        dao.deleteEntity(testEntityDataSet.getEntity());
        verifyExpectedData(emptyDataSet);
    }

    @Test
    public void deleteEntity_transactionFailure() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        try {
            Field sessionManagerField = AbstractSingleEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        doThrow(new RuntimeException()).when(session).remove(testEntityDataSet.getEntity());

        assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(testEntityDataSet.getEntity());
        });

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    void findEntityAndUpdate_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);

        dao.findEntityAndUpdate(updateEntityDataSet.getEntity(), parameter);
        verifyExpectedData(updateEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void findEntityAndUpdate_transactionFailure() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        IThreadLocalSessionManager mockSessionManager = mock(IThreadLocalSessionManager.class);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        when(mockSessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        try {
            Field sessionManagerField = AbstractSingleEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, mockSessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        when(mockSessionManager.getSession()).thenReturn(session);
        doThrow(new RuntimeException()).when(session).merge(testEntityDataSet.getEntity());

        Parameter parameter = new Parameter("id", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndUpdate(updateEntityDataSet.getEntity(), parameter);
        });

        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(mockSessionManager).closeSession();
    }

    @Test
    void findEntityAndDelete_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData(emptyDataSet);
    }

    @Test
    void findEntityAndDelete_transactionFailure() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        IThreadLocalSessionManager mockSessionManager = mock(IThreadLocalSessionManager.class);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        when(mockSessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        try {
            Field sessionManagerField = AbstractSingleEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, mockSessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        when(mockSessionManager.getSession()).thenReturn(session);
        doThrow(new RuntimeException()).when(session).remove(testEntityDataSet.getEntity());

        Parameter parameter = new Parameter("id", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndDelete(parameter);
        });
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(mockSessionManager).closeSession();
    }

    @Test
    void getOptionalEntity_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);

        Optional<Object> result = dao.getOptionalEntity(parameter);

        assertTrue(result.isPresent());
        Object resultEntity = result.get();
        assertNotNull(resultEntity);
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void getOptionalEntity_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalEntity(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntity_NoResult() {
        loadDataSet(emptyDataSet);
        Parameter parameter = new Parameter("id", 100L);

        Optional<Object> result = dao.getOptionalEntity(parameter);

        assertTrue(result.isEmpty());
        verifyExpectedData(emptyDataSet);
    }

    @Test
    void getEntity_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);
        Object result = dao.getEntity(parameter);

        assertNotNull(result);
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void getEntity_Failure() {
        Parameter parameter = new Parameter("id", 100L);
        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(parameter);
        });

    }

    @Test
    void getOptionalEntityWithClass_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        Optional<Object> result = dao.getOptionalEntity(clazz, parameter);

        assertTrue(result.isPresent());
        Object resultEntity = result.get();
        assertNotNull(resultEntity);
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void getOptionalEntityWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 100L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalEntity(clazz, parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithClass_NoResult() {
        Parameter parameter = new Parameter("id", 100L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        Optional<Object> result = dao.getOptionalEntity(clazz, parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEntityWithClass_success() {
        loadDataSet(testEntityDataSet.getDataSetPath()[0]);
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        Object result = dao.getEntity(clazz, parameter);

        assertNotNull(result);
        verifyExpectedData(testEntityDataSet.getDataSetPath()[0]);
    }

    @Test
    void getEntityWithClass_Failure() {
        Parameter parameter = new Parameter("id", 100L);
        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(clazz, parameter);
        });

    }

}
