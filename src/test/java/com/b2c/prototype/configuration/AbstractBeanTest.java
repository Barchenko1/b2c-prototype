package com.b2c.prototype.configuration;

import com.b2c.prototype.mapper.TestUtil;
import com.tm.core.dao.single.ISingleEntityDao;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static com.b2c.prototype.constant.Constant.POSTGRES_DRIVER;
import static com.b2c.prototype.constant.Constant.POSTGRES_TEST_CLIENT_DB_URL;
import static com.b2c.prototype.constant.Constant.POSTGRES_TEST_CLIENT_PASSWORD;
import static com.b2c.prototype.constant.Constant.POSTGRES_TEST_CLIENT_USERNAME;
import static com.b2c.prototype.mapper.Query.SELECT_BY_PARAM;

public abstract class AbstractBeanTest {

    protected static SessionFactory sessionFactory;
    protected static DataSource dataSource = getClientHikariDataSource();
    protected static AnnotationConfigApplicationContext context;

    @AfterAll
    public static void cleanUp() throws SQLException {
        context.close();
        TestUtil.cleanUp(sessionFactory);
        dataSource.getConnection().close();
    }

    public static HikariDataSource getClientHikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(POSTGRES_TEST_CLIENT_DB_URL);
        dataSource.setUsername(POSTGRES_TEST_CLIENT_USERNAME);
        dataSource.setPassword(POSTGRES_TEST_CLIENT_PASSWORD);
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
        return dataSource;
    }

    protected Object getEntity(ISingleEntityDao entityDao, String table, String paramName, String paramValue) {
        return entityDao.getEntityBySQLQuery(
                String.format(SELECT_BY_PARAM, table, paramName, paramValue));
    }

    protected Object getEntity(ISingleEntityDao entityDao, String table, String paramName, Integer paramValue) {
        return entityDao.getEntityBySQLQuery(
                String.format(SELECT_BY_PARAM, table, paramName, paramValue));
    }

    protected Object getEntity(ISingleEntityDao entityDao, String table, String paramName, Long paramValue) {
        return entityDao.getEntityBySQLQuery(
                String.format(SELECT_BY_PARAM, table, paramName, paramValue));
    }

    protected Object getEntityTest(ISingleEntityDao entityDao, String table, String paramName, Long paramValue) {
        return entityDao.getEntityBySQLQuery(
                String.format("SELECT * FROM %s e JOIN where e.%s = '%s'", table, paramName, paramValue));
    }

    protected <T> void cleanDbByDao(ISingleEntityDao entityDao, String query) {
        List<T> firstOrganizationList = entityDao.getEntityListBySQLQuery(query);
        if (!firstOrganizationList.isEmpty()) {
            firstOrganizationList.forEach(entityDao::deleteEntity);
        }
    }

    protected <T> boolean isTableEmpty(ISingleEntityDao entityDao, String query) {
        List<T> organizationList = entityDao.getEntityListBySQLQuery(query);
        return organizationList.isEmpty();
    }

}
