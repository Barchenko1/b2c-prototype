package com.b2c.prototype.dao;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.YamlDataSet;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import jakarta.persistence.NoResultException;
import org.dbunit.database.DatabaseConnection;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import com.github.database.rider.core.dsl.RiderDSL;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.dao.ConfigureSessionFactoryTest.getSessionFactory;
import static com.b2c.prototype.dao.DataSourcePool.getHikariDataSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
//import org.junit.runner.RunWith;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DBRider
public abstract class AbstractSingleEntityDaoTest {

    protected static SessionFactory sessionFactory;
    protected static SessionFactory mockSessionFactory;
    protected static Session session;
    protected static Transaction transaction;

    @Mock
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

    public static void setupCommon() {
        mockSessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);

        when(mockSessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @BeforeAll
    public static void setUpAll() {
        DataSource dataSource = getHikariDataSource();
        connectionHolder = dataSource::getConnection;
        executor = DataSetExecutorImpl.instance("executor-name", connectionHolder);

        try {
            RiderDSL.withConnection(connectionHolder.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sessionFactory = getSessionFactory();
    }

    @BeforeEach
    public void setUp() {
        try {
            Field entityIdentifierDaoField = AbstractSingleEntityDao.class.getDeclaredField("entityIdentifierDao");
            entityIdentifierDaoField.setAccessible(true);
            entityIdentifierDaoField.set(dao, entityIdentifierDao);

            Field sessionFactoryField = AbstractSingleEntityDao.class.getDeclaredField("sessionFactory");
            sessionFactoryField.setAccessible(true);
            sessionFactoryField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        loadDataSet(testEntityDataSet.getDataSetPath());
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        when(entityIdentifierDao.getEntityList(eq(clazz), eq(parameter))).thenReturn(List.of(testEntityDataSet.getEntity()));
        List<Object> result = dao.getEntityList(parameter);

        assertEquals(1, result.size());
        verifyExpectedData(testEntityDataSet.getDataSetPath());
    }

    @Test
    void getEntityListWithClass_success() {
        loadDataSet(testEntityDataSet.getDataSetPath());
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        when(entityIdentifierDao.getEntityList(eq(clazz), eq(parameter))).thenReturn(List.of(testEntityDataSet.getEntity()));
        List<Object> result = dao.getEntityList(clazz, parameter);

        assertEquals(1, result.size());
        verifyExpectedData(testEntityDataSet.getDataSetPath());
    }

    @Test
    void getEntityList_Failure() {
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        doThrow(new RuntimeException()).when(entityIdentifierDao).getEntityList(clazz, parameter);
        assertThrows(RuntimeException.class, () -> {
            dao.getEntityList(parameter);
        });
    }

    @Test
    void getEntityListWithClass_Failure() {
        Parameter[] parameters = new Parameter[]{
                new Parameter("id", 1L)
        };

        Class<?> clazz = testEntityDataSet.getEntity().getClass();

        doThrow(new RuntimeException()).when(entityIdentifierDao).getEntityList(clazz, parameters);
        assertThrows(RuntimeException.class, () -> {
            dao.getEntityList(clazz, parameters);
        });
    }

    @Test
    public void saveEntity_success() {
        loadDataSet(emptyDataSet);
        dao.saveEntity(saveEntityDataSet.getEntity());
        verifyExpectedData(saveEntityDataSet.getDataSetPath());
    }

    @Test
    public void saveEntity_transactionFailure() {
        setupCommon();
        try {
            Field sessionManagerField = AbstractSingleEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, mockSessionFactory);
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
        loadDataSet(testEntityDataSet.getDataSetPath());
        dao.updateEntity(updateEntityDataSet.getEntity());
        verifyExpectedData(updateEntityDataSet.getDataSetPath());
    }

    @Test
    public void updateEntity_transactionFailure() {
        setupCommon();
        try {
            Field sessionManagerField = AbstractSingleEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, mockSessionFactory);
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
        loadDataSet(testEntityDataSet.getDataSetPath());
        dao.deleteEntity(testEntityDataSet.getEntity());
        verifyExpectedData(emptyDataSet);
    }

    @Test
    public void deleteEntity_transactionFailure() {
        setupCommon();
        try {
            Field sessionManagerField = AbstractSingleEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, mockSessionFactory);
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
        loadDataSet(testEntityDataSet.getDataSetPath());
        Parameter parameter = new Parameter("id", 100L);
        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        when(entityIdentifierDao.getEntity(eq(clazz), eq(parameter))).thenReturn(testEntityDataSet.getEntity());

        dao.findEntityAndUpdate(updateEntityDataSet.getEntity(), parameter);
        verifyExpectedData(updateEntityDataSet.getDataSetPath());
    }

    @Test
    void findEntityAndUpdate_transactionFailure() {
        loadDataSet(emptyDataSet);
        Parameter parameter = new Parameter("id", 100L);

        assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndUpdate(updateEntityDataSet.getEntity(), parameter);
        });
        verifyExpectedData(emptyDataSet);

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    void findEntityAndDelete_success() {
        loadDataSet(testEntityDataSet.getDataSetPath());
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        when(entityIdentifierDao.getEntity(eq(clazz), eq(parameter))).thenReturn(testEntityDataSet.getEntity());

        dao.findEntityAndDelete(parameter);
        verifyExpectedData(emptyDataSet);
    }

    @Test
    void findEntityAndDelete_transactionFailure() {
        loadDataSet(testEntityDataSet.getDataSetPath());
        Parameter parameter = new Parameter("id", 100L);

        assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndDelete(parameter);
        });
        verifyExpectedData(testEntityDataSet.getDataSetPath());

        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    void getOptionalEntity_success() {
        loadDataSet(testEntityDataSet.getDataSetPath());
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        when(entityIdentifierDao.getOptionalEntity(eq(clazz), eq(parameter))).thenReturn(Optional.of(testEntityDataSet.getEntity()));

        Optional<Object> result = dao.getOptionalEntity(parameter);

        assertTrue(result.isPresent());
        Object resultEntity = result.get();
        assertNotNull(resultEntity);
        verifyExpectedData(testEntityDataSet.getDataSetPath());
    }

    @Test
    void getOptionalEntity_Failure() {
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        doThrow(new RuntimeException()).when(entityIdentifierDao).getOptionalEntity(clazz, parameter);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalEntity(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntity_NoResult() {
        loadDataSet(emptyDataSet);
        Parameter parameter = new Parameter("id", 100L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        doThrow(new NoResultException()).when(entityIdentifierDao).getOptionalEntity(eq(clazz), eq(parameter));

        Optional<Object> result = dao.getOptionalEntity(parameter);

        assertTrue(result.isEmpty());
        verifyExpectedData(emptyDataSet);
    }

    @Test
    void getEntity_success() {
        loadDataSet(testEntityDataSet.getDataSetPath());
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        when(entityIdentifierDao.getEntity(eq(clazz), eq(parameter))).thenReturn(testEntityDataSet.getEntity());

        Object result = dao.getEntity(parameter);

        assertNotNull(result);
        verifyExpectedData(testEntityDataSet.getDataSetPath());
    }

    @Test
    void getEntity_Failure() {
        Parameter parameter = new Parameter("id", 100L);
        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        doThrow(new RuntimeException()).when(entityIdentifierDao).getEntity(eq(clazz), eq(parameter));

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(parameter);
        });

    }

    @Test
    void getOptionalEntityWithClass_success() {
        loadDataSet(testEntityDataSet.getDataSetPath());
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        when(entityIdentifierDao.getOptionalEntity(eq(clazz), eq(parameter))).thenReturn(Optional.of(testEntityDataSet.getEntity()));

        Optional<Object> result = dao.getOptionalEntity(clazz, parameter);

        assertTrue(result.isPresent());
        Object resultEntity = result.get();
        assertNotNull(resultEntity);
        verifyExpectedData(testEntityDataSet.getDataSetPath());
    }

    @Test
    void getOptionalEntityWithClass_Failure() {
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        doThrow(new RuntimeException()).when(entityIdentifierDao).getOptionalEntity(clazz, parameter);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalEntity(clazz, parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithClass_NoResult() {
        Parameter parameter = new Parameter("id", 100L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        doThrow(new NoResultException()).when(entityIdentifierDao).getOptionalEntity(eq(clazz), eq(parameter));

        Optional<Object> result = dao.getOptionalEntity(clazz, parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEntityWithClass_success() {
        loadDataSet(testEntityDataSet.getDataSetPath());
        Parameter parameter = new Parameter("id", 1L);

        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        when(entityIdentifierDao.getEntity(eq(clazz), eq(parameter))).thenReturn(testEntityDataSet.getEntity());

        Object result = dao.getEntity(clazz, parameter);

        assertNotNull(result);
        verifyExpectedData(testEntityDataSet.getDataSetPath());
    }

    @Test
    void getEntityWithClass_Failure() {
        Parameter parameter = new Parameter("id", 100L);
        Class<?> clazz = testEntityDataSet.getEntity().getClass();
        doThrow(new RuntimeException()).when(entityIdentifierDao).getEntity(eq(clazz), eq(parameter));

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(clazz, parameter);
        });

    }

}
