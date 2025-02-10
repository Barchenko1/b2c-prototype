package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.InitDiscountDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.OptionGroupDto;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemDataControllerE2ETest extends BasicE2ETest {

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
            statement.execute("TRUNCATE TABLE discount RESTART IDENTITY CASCADE");
            statement.execute("ALTER SEQUENCE discount_id_seq RESTART WITH 4");
            statement.execute("ALTER SEQUENCE price_id_seq RESTART WITH 6");
            statement.execute("ALTER SEQUENCE articular_item_id_seq RESTART WITH 4");
            statement.execute("ALTER SEQUENCE option_item_id_seq RESTART WITH 6");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_data", e);
        }
    }

    @Test
    void testCreateItemData() {
        loadDataSet("/datasets/item/item_data/emptyE2EItemDataSet.yml");
        ItemDataDto itemDataDto = getItemDataDto();
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemDataDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/item_data/saveE2EAllItemDataSet.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "item_id", "articular_id", "dateOfCreate", "discount_id", "fullprice_id", "totalprice_id"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );

    }

    @Test
    void testPutItemData() {
        loadDataSet("/datasets/item/item_data/testE2EAllItemDataSet.yml");
        ItemDataDto updateDto = getUpdatedItemDataDto();

        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/item_data/saveE2EAllItemDataSet.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testPatchItemData() {
        loadDataSet("/datasets/item/item_data/testE2EAllItemDataSet.yml");
        ItemDataDto updateDto = getUpdatedItemDataDto();

        try {
            mockMvc.perform(patch(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/item_data/saveE2EAllItemDataSet.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testDeleteItemData() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams())))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/item_data/deletedItemDataSet.yml");
    }

    @Test
    void testGetItemData() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
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
            ItemDataDto actual = objectMapper.readValue(jsonResponse, ItemDataDto.class);

            ItemDataDto expected = getItemDataDto();

        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    @Test
    void testGetAllItemData() {
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
                .description(
                        new LinkedHashMap<>() {{
                            put("par1", "desc1");
                            put("par2", "desc2");
                            put("par3", "desc3");
                        }}
                )
                .category("Laptop")
                .itemType("Electronic")
                .brand("TechBrand")
                .articularItemSet(Set.of(
                        ArticularItemDto.builder()
                                .productName("Laptop 1")
                                .status("NEW")
                                .options(Set.of(SingleOptionItemDto.builder()
                                                .optionGroup(OptionGroupDto.builder()
                                                        .label("Color")
                                                        .value("Color")
                                                        .build())
                                                .optionItem(OptionItemDto.builder()
                                                        .label("Red")
                                                        .value("Red")
                                                        .build())
                                                .build(),
                                        SingleOptionItemDto.builder()
                                                .optionGroup(OptionGroupDto.builder()
                                                        .label("Size")
                                                        .value("Size")
                                                        .build())
                                                .optionItem(OptionItemDto.builder()
                                                        .label("16")
                                                        .value("16")
                                                        .build())
                                                .build()))
                                .fullPrice(getPriceDto(1200))
                                .totalPrice(getPriceDto(1100))
                                .discount(getDiscountDto("abc1"))
                                .build(),
                        ArticularItemDto.builder()
                                .productName("Laptop 2")
                                .status("NEW")
                                .options(Set.of(SingleOptionItemDto.builder()
                                                .optionGroup(OptionGroupDto.builder()
                                                        .label("Color")
                                                        .value("Color")
                                                        .build())
                                                .optionItem(OptionItemDto.builder()
                                                        .label("Blue")
                                                        .value("Blue")
                                                        .build())
                                                .build(),
                                        SingleOptionItemDto.builder()
                                                .optionGroup(OptionGroupDto.builder()
                                                        .label("Size")
                                                        .value("Size")
                                                        .build())
                                                .optionItem(OptionItemDto.builder()
                                                        .label("16")
                                                        .value("16")
                                                        .build())
                                                .build()))
                                .fullPrice(getPriceDto(1200))
                                .totalPrice(getPriceDto(1100))
                                .discount(getDiscountDto("abc2"))
                                .build()
                ))
                .build();
    }

    private ItemDataDto getUpdatedItemDataDto() {
        return ItemDataDto.builder()
                .description(
                        new LinkedHashMap<>() {{
                            put("Update par1", "Update desc1");
                            put("Update par2", "Update desc2");
                            put("Update par3", "Update desc3");
                        }}
                )
                .category("Update Laptop")
                .itemType("Update Electronic")
                .brand("Update TechBrand")
                .articularItemSet(Set.of(
                        ArticularItemDto.builder()
                                .productName("Update Laptop 1")
                                .status("COMMON")
                                .options(Set.of(SingleOptionItemDto.builder()
                                                .optionGroup(OptionGroupDto.builder()
                                                        .label("Color")
                                                        .value("Color")
                                                        .build())
                                                .optionItem(OptionItemDto.builder()
                                                        .label("Red")
                                                        .value("Red")
                                                        .build())
                                                .build(),
                                        SingleOptionItemDto.builder()
                                                .optionGroup(OptionGroupDto.builder()
                                                        .label("Size")
                                                        .value("Size")
                                                        .build())
                                                .optionItem(OptionItemDto.builder()
                                                        .label("15")
                                                        .value("15")
                                                        .build())
                                                .build()))
                                .fullPrice(getPriceDto(1300))
                                .totalPrice(getPriceDto(1200))
                                .discount(getDiscountDto("Update abc1"))
                                .build(),
                        ArticularItemDto.builder()
                                .productName("Update Laptop 2")
                                .status("COMMON")
                                .options(Set.of(SingleOptionItemDto.builder()
                                                .optionGroup(OptionGroupDto.builder()
                                                        .label("Color")
                                                        .value("Color")
                                                        .build())
                                                .optionItem(OptionItemDto.builder()
                                                        .label("Red")
                                                        .value("Red")
                                                        .build())
                                                .build(),
                                        SingleOptionItemDto.builder()
                                                .optionGroup(OptionGroupDto.builder()
                                                        .label("Size")
                                                        .value("Size")
                                                        .build())
                                                .optionItem(OptionItemDto.builder()
                                                        .label("15")
                                                        .value("15")
                                                        .build())
                                                .build()))
                                .fullPrice(getPriceDto(1300))
                                .totalPrice(getPriceDto(1200))
                                .discount(getDiscountDto("Update abc2"))
                                .build()
                ))
                .build();
    }

    private InitDiscountDto getDiscountDto(String charSequenceCode) {
        return InitDiscountDto.builder()
                .amount(100)
                .charSequenceCode(charSequenceCode)
                .currency("USD")
                .isActive(true)
                .build();
    }

    private PriceDto getPriceDto(double amount) {
        return PriceDto.builder()
                .amount(amount)
                .currency("USD")
                .build();
    }

    private Map<String, String> getRequestParams() {
        return Map.of("itemId", "123");
    }
}
