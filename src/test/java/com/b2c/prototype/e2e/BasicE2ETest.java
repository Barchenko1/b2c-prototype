package com.b2c.prototype.e2e;

import com.b2c.prototype.DataBaseLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import java.util.Map;

import static com.b2c.prototype.dao.DataSourcePool.getPostgresDataSource;
import static com.b2c.prototype.dao.DatabaseQueries.cleanDatabase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "unittest"})
public class BasicE2ETest extends DataBaseLoader {

    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected static ConnectionHolder connectionHolder;
    private static DataSetExecutor executor;

    public BasicE2ETest() {
        super(connectionHolder, executor);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

//    @AfterAll
//    public static void tearDown() {
//        cleanDatabase(connectionHolder);
//    }
//
//    @BeforeAll
//    public static void setUpAll() {
//        DataSource dataSource = getPostgresDataSource();
//        connectionHolder = dataSource::getConnection;
//        executor = DataSetExecutorImpl.instance("e2e", connectionHolder);
//    }
//
    protected MultiValueMap<String, String> getMultiValueMap(Map<String, String> requestParams) {
        MultiValueMap<String, String> multiValueMap =  new LinkedMultiValueMap<>();
        requestParams.forEach(multiValueMap::add);
        return multiValueMap;
    }

}
