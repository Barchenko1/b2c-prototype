package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryValueDto;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_data", e);
        }
    }

    @Test
    void testCreateArticularItem() {
        ItemDataDto itemDataDto = getItemDataDto();

        loadDataSet("/datasets/item/item_data/emptyItemDataSet.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemDataDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/item_data/saveItemDataSet.yml");
    }

    @Test
    void testUpdateArticularItem() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");

        ItemDataDto updateDto = getItemDataDto();

        try {
            mockMvc.perform(put(URL_TEMPLATE + "/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/item_data/updatedItemDataSet.yml");
    }

    @Test
    void testDeleteArticularItem() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE + "/{id}", 1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/item_data/deletedItemDataSet.yml");
    }

    @Test
    void testGetArticularItem() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ItemDataDto actual = objectMapper.readValue(jsonResponse, ItemDataDto.class);

            ItemDataDto expected = getItemDataDto();

            assertEquals(expected.getDescription(), actual.getDescription());
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    @Test
    void testGetAllArticularItem() {
        loadDataSet("/datasets/item/item_data/testE2EAllItemDataSet.yml");

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
            List<ItemDataDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

            List<ItemDataDto> expectedList = List.of(
                    ItemDataDto.builder()
                            .build(),
                    ItemDataDto.builder()
                            .build()
            );

            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    private ItemDataDto getItemDataDto() {
        return ItemDataDto.builder()
                .category(CategoryValueDto.builder()
                        .label("categoryLabel")
                        .value("categoryValue")
                        .build())
                .itemType(ItemTypeDto.builder()
                        .label("itemTypeLabel")
                        .value("itemTypeValue")
                        .build())
                .brand(BrandDto.builder()
                        .label("brandLabel")
                        .value("brandValue")
                        .build())
//                .description()
                .build();
    }
}
