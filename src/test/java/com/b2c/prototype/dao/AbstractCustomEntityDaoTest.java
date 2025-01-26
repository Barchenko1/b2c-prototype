package com.b2c.prototype.dao;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.api.dataset.YamlDataSet;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import com.github.database.rider.junit5.api.DBRider;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Arrays;

import static com.b2c.prototype.dao.ConfigureSessionFactoryTest.getSessionFactory;
import static com.b2c.prototype.dao.DataSourcePool.getPostgresDataSource;
import static com.b2c.prototype.dao.DatabaseQueries.cleanDatabase;

@ExtendWith(MockitoExtension.class)
@DBRider
public abstract class AbstractCustomEntityDaoTest {

    protected static SessionFactory sessionFactory;

    protected static IEntityIdentifierDao entityIdentifierDao;

    protected static ConnectionHolder connectionHolder;
    private static DataSetExecutor executor;

    protected static IEntityDao dao;

    public AbstractCustomEntityDaoTest() {
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
            Field sessionFactoryField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
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

    protected void loadDataSet(String dataSetPath) {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            IDatabaseConnection dbConnection = new DatabaseConnection(connection);
            InputStream inputStream = this.getClass().getResourceAsStream(dataSetPath);
            if (inputStream == null) {
                throw new RuntimeException("Dataset file not found: " + dataSetPath);
            }
            IDataSet dataSet = new YamlDataSet(inputStream);
            System.out.println("Loading dataset from: " + dataSetPath);
            System.out.println("Dataset contents: " + Arrays.toString(dataSet.getTableNames()));

            DatabaseOperation.CLEAN_INSERT.execute(dbConnection, dataSet);
            connection.commit();
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
