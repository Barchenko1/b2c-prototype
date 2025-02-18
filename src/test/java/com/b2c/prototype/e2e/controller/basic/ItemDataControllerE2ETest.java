package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.InitDiscountDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.OptionGroupDto;
import com.b2c.prototype.modal.dto.payload.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;
import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryValueDto;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
import com.b2c.prototype.modal.dto.response.ResponseArticularDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
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

        verifyExpectedData("/datasets/item/item_data/updateE2EAllItemDataSet.yml",
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

        verifyExpectedData("/datasets/item/item_data/updateE2EAllItemDataSet.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testDeleteItemData() {
        loadDataSet("/datasets/item/item_data/testE2EAllItemDataSet.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams())))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/item/item_data/deleteE2EItemDataSet.yml");
    }

    @Test
    void testGetItemData() {
        loadDataSet("/datasets/item/item_data/testE2EAllItemDataSet.yml");
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
            ResponseItemDataDto actual = objectMapper.readValue(jsonResponse, ResponseItemDataDto.class);

            ResponseItemDataDto expected = createResponseItemDataDto1();

            assertEquals(expected.getItemId(), actual.getItemId());
            assertEquals(expected.getDescription(), actual.getDescription());
            assertEquals(expected.getBrand(), actual.getBrand());
            assertEquals(expected.getCategory(), actual.getCategory());
            assertEquals(expected.getItemType(), actual.getItemType());

            List<ResponseArticularDto> expectedResponseArticularDto = new ArrayList<>(expected.getArticularItemSet());
            expectedResponseArticularDto.sort(Comparator.comparing(ResponseArticularDto::getArticularId));
            List<ResponseArticularDto> actualResponseArticularDto = new ArrayList<>(actual.getArticularItemSet());
            actualResponseArticularDto.sort(Comparator.comparing(ResponseArticularDto::getArticularId));
            checkResponseArticularItemByIndex(expectedResponseArticularDto, actualResponseArticularDto, 0);
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
            List<ResponseItemDataDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

            List<ResponseItemDataDto> expectedList = getResponseItemDataDtoList();

            List<ResponseArticularDto> expectedResponseArticularDto0 = new ArrayList<>(expectedList.get(0).getArticularItemSet());
            expectedResponseArticularDto0.sort(Comparator.comparing(ResponseArticularDto::getArticularId));
            List<ResponseArticularDto> actualResponseArticularDto0 = new ArrayList<>(actualList.get(0).getArticularItemSet());
            actualResponseArticularDto0.sort(Comparator.comparing(ResponseArticularDto::getArticularId));
            checkResponseArticularItemByIndex(expectedResponseArticularDto0, actualResponseArticularDto0, 0);

            List<ResponseArticularDto> expectedResponseArticularDto1 = new ArrayList<>(expectedList.get(1).getArticularItemSet());
            expectedResponseArticularDto1.sort(Comparator.comparing(ResponseArticularDto::getArticularId));
            List<ResponseArticularDto> actualResponseArticularDto1 = new ArrayList<>(actualList.get(1).getArticularItemSet());
            actualResponseArticularDto1.sort(Comparator.comparing(ResponseArticularDto::getArticularId));
            checkResponseArticularItemByIndex(expectedResponseArticularDto1, actualResponseArticularDto1, 0);
            checkResponseArticularItemByIndex(expectedResponseArticularDto1, actualResponseArticularDto1, 1);

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
                .category(CategoryValueDto.builder()
                        .label("Laptop")
                        .value("Laptop")
                        .build())
                .itemType(ItemTypeDto.builder()
                        .label("Electronic")
                        .value("Electronic")
                        .build())
                .brand(BrandDto.builder()
                        .label("TechBrand")
                        .value("TechBrand")
                        .build())
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
                .category(CategoryValueDto.builder()
                        .label("Update Laptop")
                        .value("Update Laptop")
                        .build())
                .itemType(ItemTypeDto.builder()
                        .label("Update Electronic")
                        .value("Update Electronic")
                        .build())
                .brand(BrandDto.builder()
                        .label("Update TechBrand")
                        .value("Update TechBrand")
                        .build())
                .articularItemSet(Set.of(
                        ArticularItemDto.builder()
                                .articularId("2")
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
                                .articularId("3")
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

    private ResponseItemDataDto createResponseItemDataDto0() {
        return ResponseItemDataDto.builder()
                .itemId("122")
                .description(new LinkedHashMap<>() {{
                    put("par1", "desc1");
                    put("par2", "desc2");
                    put("par3", "desc3");
                }})
                .category(CategoryValueDto.builder()
                        .label("Laptop")
                        .value("Laptop")
                        .build())
                .itemType(ItemTypeDto.builder()
                        .label("Electronic")
                        .value("Electronic")
                        .build())
                .brand(BrandDto.builder()
                        .label("TechBrand")
                        .value("TechBrand")
                        .build())
                .articularItemSet(Set.of(
                        createArticularItemDto("1", "Mob 1", "xxx", 1000, 1000,
                                Set.of(createOptionGroup("Color", "Color", "Red"),
                                        createOptionGroup("Size", "Size", "16")))
                ))
                .build();
    }

    private ResponseItemDataDto createResponseItemDataDto1() {
        return ResponseItemDataDto.builder()
                .itemId("123")
                .description(new LinkedHashMap<>() {{
                    put("par1", "desc1");
                    put("par2", "desc2");
                    put("par3", "desc3");
                }})
                .category(CategoryValueDto.builder()
                        .label("Laptop")
                        .value("Laptop")
                        .build())
                .itemType(ItemTypeDto.builder()
                        .label("Electronic")
                        .value("Electronic")
                        .build())
                .brand(BrandDto.builder()
                        .label("TechBrand")
                        .value("TechBrand")
                        .build())
                .articularItemSet(Set.of(
                        createArticularItemDto("2", "Laptop 1", "abc1", 1200, 1100,
                                Set.of(createOptionGroup("Color", "Color", "Red"),
                                        createOptionGroup("Size", "Size", "16"))),
                        createArticularItemDto("3", "Laptop 2", "abc2", 1200, 1100,
                                Set.of(createOptionGroup("Color", "Color", "Blue"),
                                        createOptionGroup("Size", "Size", "16")))
                ))
                .build();
    }

    private ResponseArticularDto createArticularItemDto(String articularId, String productName, String discountCode, double fullPriceAmount, double totalPriceAmount, Set<OptionGroupOptionItemSetDto> options) {
        return ResponseArticularDto.builder()
                .articularId(articularId)
                .productName(productName)
                .fullPrice(getPriceDto(fullPriceAmount))
                .totalPrice(getPriceDto(totalPriceAmount))
                .status(ConstantPayloadDto.builder()
                        .label("NEW")
                        .value("NEW")
                        .build())
                .discount(DiscountDto.builder()
                        .charSequenceCode(discountCode)
                        .amount(100)
                        .currency("USD")
                        .isPercent(false)
                        .isActive(true)
                        .build())
                .options(options)
                .build();
    }

    private OptionGroupOptionItemSetDto createOptionGroup(String optionGroupLabel, String optionGroupValue, String optionItemValue) {
        return OptionGroupOptionItemSetDto.builder()
                .optionGroup(OptionGroupDto.builder()
                        .label(optionGroupLabel)
                        .value(optionGroupValue)
                        .build())
                .optionItems(Set.of(
                        OptionItemDto.builder()
                                .label(optionItemValue)
                                .value(optionItemValue)
                                .build()
                ))
                .build();
    }


    private List<ResponseItemDataDto> getResponseItemDataDtoList() {
        return List.of(
                createResponseItemDataDto0(),
                createResponseItemDataDto1()
        );
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

    private void checkResponseArticularItemByIndex(List<ResponseArticularDto> expectedResponseArticularItemList, List<ResponseArticularDto> actualResponseArticularItemList, int index) {
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
