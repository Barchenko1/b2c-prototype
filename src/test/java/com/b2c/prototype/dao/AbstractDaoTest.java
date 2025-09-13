package com.b2c.prototype.dao;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static com.b2c.prototype.dao.DataSourcePool.getPostgresDataSource;

@DBRider
@DBUnit(schema = "public", caseSensitiveTableNames = true)
@SpringBootTest
//@ExtendWith(DBUnitExtension.class)
public abstract class AbstractDaoTest {
    @Autowired
    private DataSource dataSource;

    @BeforeAll
    public static void setUpAll() {
        DataSource dataSource = getPostgresDataSource();
        ConnectionHolder connectionHolder = dataSource::getConnection;
        DataSetExecutor executor = DataSetExecutorImpl.instance("dao", connectionHolder);
    }

}