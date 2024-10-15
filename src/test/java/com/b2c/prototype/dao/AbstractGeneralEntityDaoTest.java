package com.b2c.prototype.dao;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.api.dataset.YamlDataSet;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import com.github.database.rider.junit5.api.DBRider;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.tm.core.dao.general.IGeneralEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.b2c.prototype.dao.ConfigureSessionFactoryTest.getSessionFactory;
import static com.b2c.prototype.dao.DataSourcePool.getHikariDataSource;
import static com.b2c.prototype.dao.DatabaseQueries.cleanDatabase;

@ExtendWith(MockitoExtension.class)
@DBRider
public abstract class AbstractGeneralEntityDaoTest {

    protected static IThreadLocalSessionManager sessionManager;
    protected static SessionFactory sessionFactory;

    protected static IEntityIdentifierDao entityIdentifierDao;

    private static ConnectionHolder connectionHolder;
    private static DataSetExecutor executor;

    protected static IGeneralEntityDao dao;

    public AbstractGeneralEntityDaoTest() {
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
            Field sessionMenagerField = AbstractGeneralEntityDao.class.getDeclaredField("sessionManager");
            sessionMenagerField.setAccessible(true);
            sessionMenagerField.set(dao, sessionManager);

            Field sessionFactoryField = AbstractGeneralEntityDao.class.getDeclaredField("sessionFactory");
            sessionFactoryField.setAccessible(true);
            sessionFactoryField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {
        cleanDatabase(connectionHolder);
    }

    protected void loadDataSet(String dataSetPath) {
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

    protected void verifyExpectedData(String dataSetPath) {
        DataSetConfig dataSetConfig = new DataSetConfig(dataSetPath);
        String[] expectedDataSets = new String[]{dataSetPath};

        try {
            executor.compareCurrentDataSetWith(dataSetConfig, expectedDataSets);
        } catch (DatabaseUnitException e) {
            throw new RuntimeException("Dataset comparison failed", e);
        }
    }

}
