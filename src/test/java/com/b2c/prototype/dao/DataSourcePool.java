package com.b2c.prototype.dao;

import com.zaxxer.hikari.HikariDataSource;

public final class DataSourcePool {

    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    private static final String POSTGRES_DB_URL = "jdbc:postgresql://127.0.0.1:5433/test_client_db";
    private static final String POSTGRES_USERNAME = "admin";
    private static final String POSTGRES_PASSWORD = "secret";
    private static final String POSTGRES_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
    private static final int MAX_POOL_SIZE = 20;
    private static final int MIN_IDLE = 5;
    private static final long CONNECTION_TIMEOUT = 60000; // 10 seconds
    private static final long IDLE_TIMEOUT = 600000; // 10 minutes
    private static final long MAX_LIFETIME = 1800000; // 30 minutes

    private static HikariDataSource dataSource;

    private DataSourcePool() {}

    public static HikariDataSource getHikariDataSource() {
        synchronized (DataSourcePool.class) {
            if (dataSource == null) {
                dataSource = new HikariDataSource();
                dataSource.setJdbcUrl(POSTGRES_DB_URL);
                dataSource.setUsername(POSTGRES_USERNAME);
                dataSource.setPassword(POSTGRES_PASSWORD);
                dataSource.setDriverClassName(POSTGRES_DRIVER);

                dataSource.setMaximumPoolSize(MAX_POOL_SIZE);
                dataSource.setMinimumIdle(MIN_IDLE);
                dataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
                dataSource.setIdleTimeout(IDLE_TIMEOUT);
                dataSource.setMaxLifetime(MAX_LIFETIME);
            }

            return dataSource;
        }
    }

    public static synchronized void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null; // Reset the instance for garbage collection
        }
    }

}
