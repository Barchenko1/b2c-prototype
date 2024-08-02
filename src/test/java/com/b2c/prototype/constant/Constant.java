package com.b2c.prototype.constant;

public interface Constant {
    String POSTGRES_DRIVER = "org.postgresql.Driver";
    String POSTGRES_TEST_CLIENT_DB_URL = "jdbc:postgresql://127.0.0.1:5433/test_client_db";
    String POSTGRES_TEST_CLIENT_USERNAME = "admin";
    String POSTGRES_TEST_CLIENT_PASSWORD = "secret";

    String POSTGRES_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";

}
