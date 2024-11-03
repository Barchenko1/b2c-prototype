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

    private static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    private static final String SQLITE_DB_URL = "jdbc:sqlite:data/cachetestdb.sqlite";

    private static HikariDataSource postgresDataSource;
    private static HikariDataSource sqliteDataSource;

    private DataSourcePool() {}

    public static HikariDataSource getPostgresDataSource() {
        synchronized (DataSourcePool.class) {
            if (postgresDataSource == null) {
                postgresDataSource = new HikariDataSource();
                postgresDataSource.setJdbcUrl(POSTGRES_DB_URL);
                postgresDataSource.setUsername(POSTGRES_USERNAME);
                postgresDataSource.setPassword(POSTGRES_PASSWORD);
                postgresDataSource.setDriverClassName(POSTGRES_DRIVER);

                postgresDataSource.setMaximumPoolSize(MAX_POOL_SIZE);
                postgresDataSource.setMinimumIdle(MIN_IDLE);
                postgresDataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
                postgresDataSource.setIdleTimeout(IDLE_TIMEOUT);
                postgresDataSource.setMaxLifetime(MAX_LIFETIME);
            }

            return postgresDataSource;
        }
    }

    public static HikariDataSource getSqliteDataSource() {
        synchronized (DataSourcePool.class) {
            if (sqliteDataSource == null) {
                sqliteDataSource = new HikariDataSource();
                sqliteDataSource.setJdbcUrl(SQLITE_DB_URL);
                sqliteDataSource.setDriverClassName(SQLITE_DRIVER);

                sqliteDataSource.setMaximumPoolSize(MAX_POOL_SIZE);
                sqliteDataSource.setMinimumIdle(MIN_IDLE);
                sqliteDataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
                sqliteDataSource.setIdleTimeout(IDLE_TIMEOUT);
                sqliteDataSource.setMaxLifetime(MAX_LIFETIME);
            }

            return sqliteDataSource;
        }
    }


}
