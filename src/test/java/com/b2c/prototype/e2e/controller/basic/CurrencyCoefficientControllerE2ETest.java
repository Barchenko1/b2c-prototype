package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.payload.commission.ResponseBuyerCommissionInfoDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
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

public class CurrencyCoefficientControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/currency/coefficient";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
//            statement.execute("DELETE FROM min_max_commission");
//            statement.execute("ALTER SEQUENCE min_max_commission_id_seq RESTART WITH 1");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: device", e);
        }
    }

    @Test
    void testSaveBuyerCommission() {
        loadDataSet("/datasets/commission/emptyE2ECommission.yml");
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/commission/input/MinMaxBuyerCommissionDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/commission/testE2EBuyerCommission.yml",
                new String[] {"id", "dateOfCreate", "user_id", "lastUpdateTimestamp", },
                new String[] {"label", "value", "fee_type"}
        );
    }

    @Test
    void testDeleteCommission() {
        loadDataSet("/datasets/commission/testE2EBuyerCommission.yml");
        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/commission/emptyE2ECommission.yml",
                new String[] {"id", "dateOfCreate", "user_id", "lastUpdateTimestamp"},
                new String[] {"label", "value", "fee_type"}
        );
    }

    @Test
    void testGetCommission() {
        loadDataSet("/datasets/commission/testAllE2ECommission.yml");

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
            ResponseMinMaxCommissionDto actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/commission/output/ResponseMinMaxCommissionDto.json");
            ResponseMinMaxCommissionDto expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetCommissions() {
        loadDataSet("/datasets/commission/testAllE2ECommission.yml");

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
            List<ResponseMinMaxCommissionDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/commission/output/ResponseAllMinMaxCommissionDto.json");
            List<ResponseMinMaxCommissionDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestParams() {
        return Map.of("commissionType", "buyer");
    }

}
