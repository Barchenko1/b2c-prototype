package com.b2c.prototype;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.api.dataset.YamlDataSet;
import com.github.database.rider.core.configuration.DataSetConfig;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Arrays;

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

}
