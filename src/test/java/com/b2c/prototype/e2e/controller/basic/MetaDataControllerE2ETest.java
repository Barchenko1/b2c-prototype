package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MetaDataControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/itemdata";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM articular_item_option_item");
            statement.execute("DELETE FROM articular_item");
            statement.execute("DELETE FROM discount");
            statement.execute("DELETE FROM item_data");

            statement.execute("ALTER SEQUENCE discount_id_seq RESTART WITH 4");
            statement.execute("ALTER SEQUENCE price_id_seq RESTART WITH 6");
            statement.execute("ALTER SEQUENCE articular_item_id_seq RESTART WITH 4");
            statement.execute("ALTER SEQUENCE option_group_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE option_item_id_seq RESTART WITH 6");
            statement.execute("ALTER SEQUENCE item_data_id_seq RESTART WITH 3");
            statement.execute("ALTER SEQUENCE category_id_seq RESTART WITH 3");
            statement.execute("ALTER SEQUENCE item_type_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE brand_id_seq RESTART WITH 2");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: metadata", e);
        }
    }

    @Test
    void testCreateItemData() {
        loadDataSet("/datasets/dao/item/metadata/emptyE2EItemDataSet.yml");
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/itemdata/input/ItemDataDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/dao/item/metadata/saveE2EAllItemDataSet.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "item_id", "articular_id", "dateOfCreate", "discount_id", "fullprice_id", "totalprice_id"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );

    }

    @Test
    void testPutItemData() {
        loadDataSet("/datasets/dao/item/metadata/testE2EAllItemDataSet.yml");

        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/itemdata/input/UpdateItemDataDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/dao/item/metadata/updateE2EAllItemDataSet.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testPatchItemData() {
        loadDataSet("/datasets/dao/item/metadata/testE2EAllItemDataSet.yml");

        try {
            mockMvc.perform(patch(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/itemdata/input/UpdateItemDataDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/dao/item/metadata/updateE2EAllItemDataSet.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testDeleteItemData() {
        loadDataSet("/datasets/dao/item/metadata/testE2EAllItemDataSet.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams())))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/dao/item/metadata/deleteE2EItemDataSet.yml");
    }

    @Test
    void testGetItemData() {
        loadDataSet("/datasets/dao/item/metadata/testE2EAllItemDataSet.yml");
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
            ResponseMetaDataDto actual = objectMapper.readValue(jsonResponse, ResponseMetaDataDto.class);

            String expectedResultStr = TestUtil.readFile("json/itemdata/output/ResponseItemDataDto.json");
            ResponseMetaDataDto expected = objectMapper.readValue(expectedResultStr, ResponseMetaDataDto.class);

            assertEquals(expected, actual);
            assertEquals(expected.getItemId(), actual.getItemId());
            assertEquals(expected.getDescription(), actual.getDescription());
            assertEquals(expected.getBrand(), actual.getBrand());
            assertEquals(expected.getCategory(), actual.getCategory());
            assertEquals(expected.getItemType(), actual.getItemType());

            List<ResponseArticularItemDto> expectedResponseArticularDto = new ArrayList<>(expected.getArticularItems());
            expectedResponseArticularDto.sort(Comparator.comparing(ResponseArticularItemDto::getArticularId));
            List<ResponseArticularItemDto> actualResponseArticularDto = new ArrayList<>(actual.getArticularItems());
            actualResponseArticularDto.sort(Comparator.comparing(ResponseArticularItemDto::getArticularId));
            checkResponseArticularItemByIndex(expectedResponseArticularDto, actualResponseArticularDto, 0);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllItemData() {
        loadDataSet("/datasets/dao/item/metadata/testE2EAllItemDataSet.yml");

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
            List<ResponseMetaDataDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/itemdata/output/ResponseItemDataDtoList.json");
            List<ResponseMetaDataDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
            List<ResponseArticularItemDto> expectedResponseArticularDto0 = new ArrayList<>(expectedList.get(0).getArticularItems());
            expectedResponseArticularDto0.sort(Comparator.comparing(ResponseArticularItemDto::getArticularId));
            List<ResponseArticularItemDto> actualResponseArticularDto0 = new ArrayList<>(actualList.get(0).getArticularItems());
            actualResponseArticularDto0.sort(Comparator.comparing(ResponseArticularItemDto::getArticularId));
            checkResponseArticularItemByIndex(expectedResponseArticularDto0, actualResponseArticularDto0, 0);

            List<ResponseArticularItemDto> expectedResponseArticularDto1 = new ArrayList<>(expectedList.get(1).getArticularItems());
            expectedResponseArticularDto1.sort(Comparator.comparing(ResponseArticularItemDto::getArticularId));
            List<ResponseArticularItemDto> actualResponseArticularDto1 = new ArrayList<>(actualList.get(1).getArticularItems());
            actualResponseArticularDto1.sort(Comparator.comparing(ResponseArticularItemDto::getArticularId));
            checkResponseArticularItemByIndex(expectedResponseArticularDto1, actualResponseArticularDto1, 0);
            checkResponseArticularItemByIndex(expectedResponseArticularDto1, actualResponseArticularDto1, 1);

        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestParams() {
        return Map.of("itemId", "123");
    }

    private void checkResponseArticularItemByIndex(List<ResponseArticularItemDto> expectedResponseArticularItemList, List<ResponseArticularItemDto> actualResponseArticularItemList, int index) {
        assertEquals(expectedResponseArticularItemList.size(), actualResponseArticularItemList.size());
        assertEquals(expectedResponseArticularItemList.get(index).getArticularId(), actualResponseArticularItemList.get(index).getArticularId());
        assertEquals(expectedResponseArticularItemList.get(index).getProductName(), actualResponseArticularItemList.get(index).getProductName());
        assertEquals(expectedResponseArticularItemList.get(index).getFullPrice(), actualResponseArticularItemList.get(index).getFullPrice());
        assertEquals(expectedResponseArticularItemList.get(index).getTotalPrice(), actualResponseArticularItemList.get(index).getTotalPrice());
        assertEquals(expectedResponseArticularItemList.get(index).getStatus().getLabel(), actualResponseArticularItemList.get(index).getStatus().getLabel());
        assertEquals(expectedResponseArticularItemList.get(index).getStatus().getValue(), actualResponseArticularItemList.get(index).getStatus().getValue());
        assertEquals(expectedResponseArticularItemList.get(index).getDiscount(), actualResponseArticularItemList.get(index).getDiscount());
        assertEquals(expectedResponseArticularItemList.get(index).getOptions(), actualResponseArticularItemList.get(index).getOptions());
    }
}
