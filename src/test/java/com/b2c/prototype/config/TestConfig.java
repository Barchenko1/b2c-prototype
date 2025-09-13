//package com.b2c.prototype.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import javax.sql.DataSource;
//
//import static com.b2c.prototype.dao.DataSourcePool.getPostgresDataSource;
//
//@Configuration
//@Profile("unittest")
//public class TestConfig {
//
//    @Bean
//    public DataSource dataSource() {
//        return getPostgresDataSource();
//    }
//}