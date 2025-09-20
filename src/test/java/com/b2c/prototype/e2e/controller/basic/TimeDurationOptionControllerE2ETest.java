package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.payload.option.ResponseTimeDurationOptionDto;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimeDurationOptionControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/timeduration";

    @BeforeEach
    public void setup() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM time_duration_option");
            statement.execute("DELETE FROM price");
            statement.execute("ALTER SEQUENCE time_duration_option_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE price_id_seq RESTART WITH 2");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: time_duration_option", e);
        }
    }

    @Test
    public void testSaveTimeDurationOption() {
        loadDataSet("/datasets/e2e/item/time_duration_option/emptyE2ETimeDurationOption.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/option/input/TimeDurationOptionDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/e2e/item/time_duration_option/testE2ETimeDurationOption.yml");
    }

    @Test
    public void testUpdateTimeDurationOption() {
        loadDataSet("/datasets/e2e/item/time_duration_option/testE2ETimeDurationOption.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/option/input/UpdateTimeDurationOptionDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/e2e/item/time_duration_option/updateE2ETimeDurationOption.yml");
    }

    @Test
    public void testDeleteTimeDurationOption() {
        loadDataSet("/datasets/e2e/item/time_duration_option/testE2ETimeDurationOption.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.TEXT_PLAIN))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/e2e/item/time_duration_option/deleteE2ETimeDurationOption.yml");
    }

    @Test
    public void testGetTimeDurationOption() {
        loadDataSet("/datasets/e2e/item/time_duration_option/testE2ETimeDurationOption.yml");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ResponseTimeDurationOptionDto actual = objectMapper.readValue(jsonResponse, ResponseTimeDurationOptionDto.class);
            String expectedResultStr = TestUtil.readFile("json/option/output/ResponseTimeDurationOptionDto.json");
            ResponseTimeDurationOptionDto expected = objectMapper.readValue(expectedResultStr, ResponseTimeDurationOptionDto.class);
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetTimeDurationOptionList() {
        loadDataSet("/datasets/e2e/item/time_duration_option/testE2ETimeDurationOption.yml");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/all")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseTimeDurationOptionDto> actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/option/output/ResponseAllTimeDurationOptionDto.json");
            List<ResponseTimeDurationOptionDto> expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestParams() {
        return Map.of("value", "9-11");
    }

}
