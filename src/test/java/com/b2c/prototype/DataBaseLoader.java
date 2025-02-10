package com.b2c.prototype;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.api.dataset.YamlDataSet;
import com.github.database.rider.core.configuration.DataSetConfig;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeTable;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.operation.DatabaseOperation;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

public class DataBaseLoader {

    private final ConnectionHolder connectionHolder;
    private final DataSetExecutor executor;

    public DataBaseLoader(ConnectionHolder connectionHolder, DataSetExecutor executor) {
        this.connectionHolder = connectionHolder;
        this.executor = executor;
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
        try {
            executor.compareCurrentDataSetWith(dataSetConfig, new String[] {
                    "id", "option_group_id", "option_item_id", "articular_item_id", "item_id", "articular_id", "dateOfCreate"});
        } catch (DatabaseUnitException e) {
            throw new RuntimeException("Dataset comparison failed", e);
        }
    }

    protected void verifyExpectedData(String dataSetPath, String[] ignoreCols, String[] orderBy) {
        DataSetConfig dataSetConfig = new DataSetConfig(dataSetPath);
        try {
            executor.compareCurrentDataSetWith(dataSetConfig, ignoreCols, new Class[]{}, orderBy);
        } catch (DatabaseUnitException e) {
            throw new RuntimeException("Dataset comparison failed", e);
        }
    }

//    protected void verifyExpectedDataDiscount(String dataSetPath) {
//        DataSetConfig dataSetConfig = new DataSetConfig(dataSetPath);
//        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dataSetPath)) {
//            IDataSet expectedDataSet = new YamlDataSet(inputStream);
//
//            ITable expectedTable = new SortedTable(expectedDataSet.getTable("DISCOUNT"), new String[]{"charSequenceCode"});
//            ITable actualTable = new SortedTable(fetchActualTableFromDb("DISCOUNT"), new String[]{"charSequenceCode"});
//
//            Assertion.assertEquals(expectedTable, actualTable);
//        } catch (Exception e) {
//            throw new RuntimeException("Dataset comparison failed", e);
//        }
//    }
//
//    private ITable fetchActualTableFromDb(String tableName) {
//        String query = "SELECT * FROM " + tableName + " ORDER BY id";
//        try {
//            IDatabaseConnection dbUnitConnection = executor.getRiderDataSource().getDBUnitConnection();
//
//            return dbUnitConnection.createQueryTable(tableName, query);
//        } catch (SQLException | DataSetException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
