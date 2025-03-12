package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.response.ResponseCommissionDto;
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

public class SellerCommissionControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/commission/seller";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM seller_commission");
            statement.execute("ALTER SEQUENCE seller_commission_id_seq RESTART WITH 3");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: device", e);
        }
    }

    @Test
    void testSaveCommission() {
        loadDataSet("/datasets/commission/emptyE2ESellerCommission.yml");
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/commission/input/SellerCommissionDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/commission/testAllE2ESellerCommission.yml",
                new String[] {"id", "dateOfCreate", "user_id", "EFFECTIVE_DATE"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testDeleteCommission() {
        loadDataSet("/datasets/commission/testAllE2ESellerCommission.yml");
        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/commission/emptyE2ESellerCommission.yml",
                new String[] {"id", "dateOfCreate", "user_id", "EFFECTIVE_DATE"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testGetCommission() {
        loadDataSet("/datasets/commission/testAllE2ESellerCommission.yml");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ResponseCommissionDto actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/commission/output/ResponseSellerCommissionDto.json");
            ResponseCommissionDto expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetCommissions() {
        loadDataSet("/datasets/commission/testAllE2ESellerCommission.yml");

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
            List<ResponseCommissionDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/commission/output/ResponseAllSellerCommissions.json");
            List<ResponseCommissionDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestParams() {
        return Map.of("effectiveDate", "2024-03-05 15:30:00");
    }

}
