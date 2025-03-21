package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageControllerE2ETest extends BasicE2ETest {
    private static final String URL_TEMPLATE = "/api/v1/message";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM messages_box");
            statement.execute("DELETE FROM messages");

            statement.execute("ALTER SEQUENCE messages_box_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE messages_seq RESTART WITH 2");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: messages", e);
        }
    }

    @Test
    void testPostUserAddress() {
        loadDataSet("/datasets/user/user_address/emptyE2EAddress.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestUserParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/address/input/UserAddressDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/user_address/testE2EAddress.yml",
                new String[] {"id", "dateOfCreate"},
                new String[] {"label", "value"}
        );
    }

    private Map<String, String> getRequestUserParams() {
        return Map.of("userId", "123");
    }
}
