package com.b2c.prototype.mapper;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.sql.DataSource;
import java.sql.Connection;

import static com.b2c.prototype.mapper.Query.SELECT_ALL;

public final class TestUtil {

    private static final ResourceReader resourceReader = new ResourceReader();

    public static void cleanUp(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static void prepareTestEntityDb(DataSource dataSource,
                                            DatabaseOperation databaseOperation,
                                            String fileName) {
        try (Connection connection = dataSource.getConnection()) {
            IDataSet dataSet = resourceReader.getDataSet(fileName);
            databaseOperation.execute(new DatabaseConnection(connection), dataSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String getQuery(String table) {
        return String.format(SELECT_ALL, table);
    }
}
