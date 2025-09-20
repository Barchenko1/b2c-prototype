package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageControllerE2ETest extends BasicE2ETest {
    private static final String URL_TEMPLATE = "/api/v1/message";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM message_box_message");
            statement.execute("DELETE FROM message_box");
            statement.execute("DELETE FROM message_template_receivers");
            statement.execute("DELETE FROM message");
            statement.execute("DELETE FROM message_template");

            statement.execute("ALTER SEQUENCE message_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE message_template_id_seq RESTART WITH 2");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: messages", e);
        }
    }

    @Test
    void testPostMessage() {
        loadDataSet("/datasets/e2e/user/message/emptyE2EMessage.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestUserParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/message/input/MessageTemplateDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/user/message/testE2EMessage.yml",
                new String[] {"id", "dateOfSend", "messageUniqNumber"},
                new String[] {"label", "value", "receiver_email"}
        );
    }

    @Test
    void testPutMessage() {
        loadDataSet("/datasets/e2e/user/message/testE2EMessage.yml");

        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestUserMessageParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/message/input/UpdateMessageDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/user/message/updateE2EMessage.yml",
                new String[] {"id", "dateOfSend", "messageUniqNumber"},
                new String[] {"label", "value", "receiver_email"}
        );
    }

    @Test
    void testChangeMessageStatus() {
        loadDataSet("/datasets/e2e/user/message/testE2EMessage.yml");
        Map<String, String> requestParams = Map.of("userId", "123", "messageId", "123", "status", "read");
        try {
            mockMvc.perform(put(URL_TEMPLATE + "/status")
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/message/input/UpdateMessageDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/user/message/updateStatusE2EMessage.yml",
                new String[] {"id", "dateOfSend", "messageUniqNumber"},
                new String[] {"label", "value", "receiver_email"}
        );
    }

    @Test
    void testDeleteByMessageId() {
        loadDataSet("/datasets/e2e/user/message/testE2EMessage.yml");
        Map<String, String> requestParams = Map.of("messageId", "123");
        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/user/message/deleteE2EMessage.yml",
                new String[] {"id", "dateOfSend", "messageUniqNumber"},
                new String[] {"label", "value", "receiver_email"}
        );
    }

    @Test
    void testDeleteByUserIdAndMessageId() {
        loadDataSet("/datasets/e2e/user/message/testE2EMessage.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestUserMessageParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/user/message/emptyE2EMessage.yml",
                new String[] {"id", "dateOfSend", "messageUniqNumber"},
                new String[] {"label", "value", "receiver_email"}
        );
    }

    @Test
    void testCleanUpMessage() {
        loadDataSet("/datasets/e2e/user/message/testE2EMessage.yml");
        try {
            mockMvc.perform(delete(URL_TEMPLATE + "/clean")
                            .params(getMultiValueMap(getRequestUserMessageParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/user/message/deleteCleanE2EMessage.yml",
                new String[] {"id", "dateOfSend", "messageUniqNumber"},
                new String[] {"label", "value", "receiver_email"}
        );
    }

    @Test
    void testGetMessageOverviewBySenderEmail() {
        loadDataSet("/datasets/e2e/user/message/testE2EMessage.yml");
        Map<String, String> requestParams = Map.of("sender", "sender.test@example.com");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/sender")
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseMessageOverviewDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/message/output/ResponseSenderMessageOverviewDtoList.json");
            List<ResponseMessageOverviewDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetMessageOverviewByReceiverEmail() {
        loadDataSet("/datasets/e2e/user/message/testE2EMessage.yml");
        Map<String, String> requestParams = Map.of("receiver", "jone.doe@example.com");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/receiver")
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseMessageOverviewDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/message/output/ResponseReceiverMessageOverviewDtoList.json");
            List<ResponseMessageOverviewDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetMessageOverviewListByUserId() {
        loadDataSet("/datasets/e2e/user/message/testE2EMessage.yml");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/overview")
                            .params(getMultiValueMap(getRequestUserParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseMessageOverviewDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/message/output/ResponseMessageOverviewDtoList.json");
            List<ResponseMessageOverviewDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            actualList.sort(Comparator.comparing(ResponseMessageOverviewDto::getSender));
            expectedList.sort(Comparator.comparing(ResponseMessageOverviewDto::getSender));
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetMessagePayloadDto() {
        loadDataSet("/datasets/e2e/user/message/testE2EMessage.yml");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/payload")
                            .params(getMultiValueMap(getRequestUserMessageParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ResponseMessagePayloadDto actualList = objectMapper.readValue(jsonResponse, ResponseMessagePayloadDto.class);
            String expectedResultStr = TestUtil.readFile("json/message/output/ResponseMessagePayloadDto.json");
            ResponseMessagePayloadDto expectedList = objectMapper.readValue(expectedResultStr, ResponseMessagePayloadDto.class);
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestUserParams() {
        return Map.of("userId", "123");
    }
    private Map<String, String> getRequestUserMessageParams() {
        return Map.of("userId", "123", "messageId", "123");
    }
}
