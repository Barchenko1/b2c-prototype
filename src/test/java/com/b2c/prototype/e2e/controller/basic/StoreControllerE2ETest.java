package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StoreControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/store";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM articular_item_quantity");
            statement.execute("DELETE FROM store");
            statement.execute("DELETE FROM address");;

            statement.execute("ALTER SEQUENCE store_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE address_id_seq RESTART WITH 2");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: store", e);
        }
    }

    @Test
    void testSaveStore() {
        loadDataSet("/datasets/store/store/emptyE2EStore.yml");
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/store/input/StoreDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/store/store/testE2EStore.yml",
                new String[] {"id", "dateOfCreate", "STORE_ID"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testUpdateStore() {
        loadDataSet("/datasets/store/store/testE2EStore.yml");

        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/store/input/UpdateStoreDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/store/store/updateE2EStore.yml",
                new String[] {"id", "dateOfCreate"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testDeleteStore() {
        loadDataSet("/datasets/store/store/testE2EStore.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                    .params(getMultiValueMap(getRequestParams())))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/store/store/emptyE2EStore.yml");
    }

    @Test
    void testGetStoreByUser() {
        loadDataSet("/datasets/store/store/testE2EStore.yml");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/123")
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ResponseStoreDto actual = objectMapper.readValue(jsonResponse, ResponseStoreDto.class);
            String expectedResultStr = TestUtil.readFile("json/store/output/ResponseStoreDto.json");
            ResponseStoreDto expected = objectMapper.readValue(expectedResultStr, ResponseStoreDto.class);
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllStoresByArticularId() {
        loadDataSet("/datasets/store/store/testE2EStore.yml");
        Map<String, String> requestParams = Map.of("articularId", "123");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseStoreDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/store/output/ResponseStoreArticularDtoList.json");
            List<ResponseStoreDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllStoresByCountry() {
        loadDataSet("/datasets/store/store/testE2EStore.yml");
        Map<String, String> requestParams = Map.of("country", "USA");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/all")
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseStoreDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/store/output/ResponseCountryStoreDtoList.json");
            List<ResponseStoreDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // fix next
//    @Test
//    void testGetAllStoresByCountryAndCity() {
//        loadDataSet("/datasets/store/store/testE2EStore.yml");
//        Map<String, String> requestParams = Map.of("country", "USA", "city", "New York");
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/all")
//                            .params(getMultiValueMap(requestParams))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            String jsonResponse = mvcResult.getResponse().getContentAsString();
//            List<ResponseStoreDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/store/output/ResponseCountryCityStoreDtoList.json");
//            List<ResponseStoreDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            assertEquals(expectedList, actualList);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private Map<String, String> getRequestParams() {
        return Map.of("storeId", "123");
    }

}
