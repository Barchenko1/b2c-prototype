package com.b2c.prototype.e2e;

import com.b2c.prototype.DataBaseLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.b2c.prototype.dao.DataSourcePool.getPostgresDataSource;
import static com.b2c.prototype.dao.DatabaseQueries.cleanDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @AfterAll
    public static void tearDown() {
        cleanDatabase(connectionHolder);
    }

    @BeforeAll
    public static void setUpAll() {
        DataSource dataSource = getPostgresDataSource();
        connectionHolder = dataSource::getConnection;
        executor = DataSetExecutorImpl.instance("e2e", connectionHolder);
    }

    protected <T> void assertMvcResult(MvcResult mvcResult, T expected) {
        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            T actual = (T) objectMapper.readValue(jsonResponse, expected.getClass());
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    protected <T> void assertMvcListResult(MvcResult mvcResult, List<T> expectedList, TypeReference<List<T>> typeReference) {
        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<T> actualList = objectMapper.readValue(jsonResponse, typeReference);
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

}
