package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticularItemControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/articular";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM articular_item_option_item");
            statement.execute("DELETE FROM articular_item");
            statement.execute("DELETE FROM discount");

            statement.execute("ALTER SEQUENCE discount_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE price_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE articular_item_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE option_group_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE option_item_id_seq RESTART WITH 6");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: articular_item", e);
        }
    }

    @Test
    void testCreateItemData() {
        loadDataSet("/datasets/item/articular_item/emptyE2EArticularItem.yml");
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .params(getMultiValueMap(getRequestParams()))
                            .content(TestUtil.readFile("json/articularitem/input/ArticularItemDtoList.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/articular_item/saveE2EArticularItem.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "articular_id", "dateOfCreate", "discount_id", "fullprice_id", "totalprice_id"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );

    }

    @Test
    void testPutItemData() {
        loadDataSet("/datasets/item/articular_item/testE2EArticularItem.yml");

        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/articularitem/input/UpdateArticularItemDtoList.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/articular_item/updateE2EArticularItem.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testPatchItemData() {
        loadDataSet("/datasets/item/articular_item/testE2EArticularItem.yml");

        try {
            mockMvc.perform(patch(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/articularitem/input/UpdateArticularItemDtoList.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/articular_item/updateE2EArticularItem.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testDeleteItemData() {
        loadDataSet("/datasets/item/articular_item/testE2EArticularItem.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(Map.of("articularId", "3"))))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/articular_item/deleteE2EArticularItem.yml");
    }

    @Test
    void testGetItemData() {
        loadDataSet("/datasets/item/articular_item/testE2EArticularItem.yml");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .params(getMultiValueMap(Map.of("articularId", "3")))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ResponseArticularItemDto actual = objectMapper.readValue(jsonResponse, ResponseArticularItemDto.class);
            String expectedResultStr = TestUtil.readFile("json/articularitem/output/ResponseArticularItemDto.json");
            ResponseArticularItemDto expected = objectMapper.readValue(expectedResultStr, ResponseArticularItemDto.class);
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllItemData() {
        loadDataSet("/datasets/item/articular_item/testE2EArticularItem.yml");

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
            List<ResponseArticularItemDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/articularitem/output/ResponseArticularItemDtoList.json");
            List<ResponseArticularItemDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestParams() {
        return Map.of("itemId", "123");
    }

}
