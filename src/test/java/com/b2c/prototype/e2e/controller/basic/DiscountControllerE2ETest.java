package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DiscountControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/discount";

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM item_data_option");
            statement.execute("DELETE FROM discount");
            statement.execute("DELETE FROM currency");
            statement.execute("TRUNCATE TABLE discount RESTART IDENTITY CASCADE");
            statement.execute("ALTER SEQUENCE discount_id_seq RESTART WITH 1");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    @Test
    void testSaveDiscount() {
        DiscountDto discountDto = discountDto();
        loadDataSet("/datasets/item/discount/emptyE2EDiscountDataSet.yml");
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(discountDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/testDiscountDataSet.yml");
    }

    @Test
    void testPutWithNewDiscountByArticularId() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("updateWithNewDiscount")
                .amount(20)
                .currency("USD")
                .isActive(true)
                .build();

        try {
            mockMvc.perform(put(URL_TEMPLATE + "/articular")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("articularId", "111")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/updateE2EDiscountByArticularIdDataSet.yml");
    }

    @Test
    void testPutCreateNewDiscountByArticularId() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("newDiscount")
                .amount(20)
                .currency("USD")
                .isActive(true)
                .build();

        try {
            mockMvc.perform(put(URL_TEMPLATE + "/articular")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("articularId", "noDiscount")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/updateE2ENewDiscountByArticularIdDataSet.yml");
    }

    @Test
    void testPutExistDiscountByArticularId() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("abc1")
                .build();

        try {
            mockMvc.perform(put(URL_TEMPLATE + "/articular")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("articularId", "noDiscount")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/updateE2EWithExistingDiscountByArticularIdDataSet.yml");
    }

    @Test
    void testPatchWithNewDiscountByArticularId() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("updateWithNewDiscount")
                .amount(20)
                .currency("USD")
                .isActive(true)
                .build();

        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/articular")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("articularId", "111")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/updateE2EDiscountByArticularIdDataSet.yml");
    }

    @Test
    void testPatchCreateNewDiscountByArticularId() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("newDiscount")
                .amount(20)
                .currency("USD")
                .isActive(true)
                .build();

        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/articular")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("articularId", "noDiscount")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/updateE2ENewDiscountByArticularIdDataSet.yml");
    }

    @Test
    void testPatchExistDiscountByArticularId() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("abc1")
                .build();

        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/articular")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("articularId", "noDiscount")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/updateE2EWithExistingDiscountByArticularIdDataSet.yml");
    }



    @Test
    void testGetDiscounts() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        List<DiscountDto> expectedList = List.of(
                DiscountDto.builder()
                        .charSequenceCode("abc")
                        .amount(10)
                        .currency("USD")
                        .isActive(true)
                        .articularIdSet(Set.of("123"))
                        .build(),
                DiscountDto.builder()
                        .charSequenceCode("abc1")
                        .amount(10)
                        .currency("USD")
                        .isActive(true)
                        .articularIdSet(Set.of("111", "122"))
                        .build()
        );

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<DiscountDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }

    }

    @Test
    void testGetDiscount() {
        DiscountDto expectedDto = discountDto();
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .param("charSequenceCode", expectedDto.getCharSequenceCode())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.charSequenceCode").value(expectedDto.getCharSequenceCode()))
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            DiscountDto actual = objectMapper.readValue(jsonResponse, DiscountDto.class);
            assertEquals(expectedDto.getArticularIdSet(), actual.getArticularIdSet());
            assertEquals(expectedDto.getAmount(), actual.getAmount());
            assertEquals(expectedDto.getCurrency(), actual.getCurrency());
            assertEquals(expectedDto.getCharSequenceCode(), actual.getCharSequenceCode());
            assertEquals(expectedDto.isActive(), actual.isActive());
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    @Test
    void testPutWithUpdateDiscountByCharSequenceCode() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("updateDiscountCode")
                .amount(20)
                .currency("USD")
                .isActive(true)
                .build();

        try {
            mockMvc.perform(put(URL_TEMPLATE + "/sequence")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("charSequenceCode", "abc1")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/updateE2EDiscountDataSet.yml");
    }

    @Test
    void testPatchUpdateDiscountByCharSequenceCode() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("updateDiscountCode")
                .amount(20)
                .currency("USD")
                .isActive(true)
                .build();

        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/sequence")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("charSequenceCode", "abc1")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/updateE2EDiscountDataSet.yml");
    }

    @Test
    void testPutUpdateDiscountByCharSequenceCode_Exception() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("abc")
                .build();

        try {
            mockMvc.perform(put(URL_TEMPLATE + "/sequence")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("charSequenceCode", "abc1")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().is5xxServerError());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/testE2EDiscountDataSet.yml");
    }

    @Test
    void testPatchUpdateDiscountByCharSequenceCode_Exception() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("abc")
                .build();

        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/sequence")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("charSequenceCode", "abc1")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().is5xxServerError());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/testE2EDiscountDataSet.yml");
    }

    @Test
    void testPatchExistDiscountByCharSequenceCodeNull_Exception() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode(null)
                .build();

        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/sequence")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("charSequenceCode", "noValue")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().is5xxServerError());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/testE2EDiscountDataSet.yml");
    }

    @Test
    void testPatchExistDiscountByArticularNull_Exception() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode(null)
                .build();

        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/articular")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("articularId", "111")
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().is5xxServerError());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/testE2EDiscountDataSet.yml");
    }

    @Test
    void testPutChangeDiscountStatus() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        DiscountDto expectedDto = DiscountDto.builder()
                .charSequenceCode("abc1")
                .isActive(false)
                .build();

        try {
            mockMvc.perform(put(URL_TEMPLATE + "/status")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(expectedDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/updateE2EDiscountChangeStatusDataSet.yml");
    }

    @Test
    void testDeleteDiscount() {
        loadDataSet("/datasets/item/discount/testE2EDiscountDataSet.yml");
        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .param("charSequenceCode", "abc1")
                            .contentType(MediaType.TEXT_PLAIN))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/discount/deleteE2EDiscountDataSet.yml");
    }

    private DiscountDto discountDto() {
        return DiscountDto.builder()
                .charSequenceCode("abc")
                .amount(10)
                .currency("USD")
                .isActive(true)
                .articularIdSet(Set.of("123"))
                .build();
    }
}
