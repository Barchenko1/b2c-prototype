//package com.b2c.prototype.e2e.controller.basic;
//
//import com.b2c.prototype.e2e.BasicE2ETest;
//import com.b2c.prototype.modal.dto.payload.option.OptionGroupDto;
//import com.b2c.prototype.modal.dto.payload.option.OptionGroupOptionItemSetDto;
//import com.b2c.prototype.modal.dto.payload.option.OptionItemDto;
//import com.b2c.prototype.modal.dto.payload.option.SingleOptionItemDto;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.io.UnsupportedEncodingException;
//import java.sql.Connection;
//import java.sql.Statement;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class OptionItemControllerE2ETest extends BasicE2ETest {
//
//    private static final String URL_TEMPLATE = "/api/v1/option";
//
////    @BeforeEach
////    public void setup() {
////        try (Connection connection = connectionHolder.getConnection()) {
////            connection.setAutoCommit(false);
////            Statement statement = connection.createStatement();
////            statement.execute("DELETE FROM articular_item_option_item");
////            statement.execute("DELETE FROM articular_item");
////            statement.execute("DELETE FROM option_item");
////            statement.execute("DELETE FROM option_group");
////            connection.commit();
////        } catch (Exception e) {
////            throw new RuntimeException("Failed to clean table: item_option", e);
////        }
////    }
//
//    @Test
//    public void testSaveOptionItemByArticularId() {
//        // loadDataSet("/datasets/e2e/item/articular_item/emptyE2EArticularIdOptionItemWithOptionGroupDataSet.yml");
//        Map<String, String> requestParams = Map.of("articularId", "1234");
//        SingleOptionItemDto singleOptionItemDto = getSingleOptionItemSizeGroupDto();
//
////        try {
////            mockMvc.perform(post(URL_TEMPLATE)
////                            .params(getMultiValueMap(requestParams))
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(objectMapper.writeValueAsString(singleOptionItemDto)))
////                    .andExpect(status().isOk());
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//        // verifyExpectedData("/datasets/e2e/item/articular_item/testE2EArticularIdOptionItemSizeDataSet.yml");
//    }
//
//    @Test
//    public void testSaveOptionItemByArticularIdWithoutOptionGroup() {
//        // loadDataSet("/datasets/e2e/item/articular_item/emptyE2EArticularIdOptionItemWithoutOptionGroupDataSet.yml");
//        Map<String, String> requestParams = Map.of("articularId", "1234");
//        SingleOptionItemDto singleOptionItemDto = getSingleOptionItemSizeGroupDto();
//
////        try {
////            mockMvc.perform(post(URL_TEMPLATE)
////                            .params(getMultiValueMap(requestParams))
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(objectMapper.writeValueAsString(singleOptionItemDto)))
////                    .andExpect(status().isOk());
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//        // verifyExpectedData("/datasets/e2e/item/articular_item/testE2EArticularIdOptionItemSizeDataSet.yml");
//    }
//
//    @Test
//    public void testSaveOptionItemByOptionGroup() {
//        // loadDataSet("/datasets/e2e/item/articular_item/emptyE2EOptionItemWithOptionGroupDataSet.yml");
//        OptionGroupOptionItemSetDto optionGroupOptionItemSetDto = getOptionItemSizeGroupDto();
//        List<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList = List.of(optionGroupOptionItemSetDto);
//
////        try {
////            mockMvc.perform(post(URL_TEMPLATE + "/group")
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(objectMapper.writeValueAsString(optionGroupOptionItemSetDtoList)))
////                    .andExpect(status().isOk());
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//        // verifyExpectedData("/datasets/e2e/item/articular_item/testE2EAllOptionItemSizeDataSet.yml");
//    }
//
//    @Test
//    public void testSaveOptionItemByOptionGroupWithOutOptionGroup() {
//        // loadDataSet("/datasets/e2e/item/articular_item/emptyE2EOptionItemWithoutOptionGroupDataSet.yml");
//        OptionGroupOptionItemSetDto optionGroupOptionItemSetDto = getOptionItemColorGroupDto();
//        List<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList = List.of(optionGroupOptionItemSetDto);
//
////        try {
////            mockMvc.perform(post(URL_TEMPLATE + "/group")
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(objectMapper.writeValueAsString(optionGroupOptionItemSetDtoList)))
////                    .andExpect(status().isOk());
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//        // verifyExpectedData("/datasets/e2e/item/articular_item/testE2EAllOptionItemColorDataSet.yml");
//    }
//
//    @Test
//    public void testUpdateOptionItemByGroup() {
//        // loadDataSet("/datasets/e2e/item/articular_item/testE2EAllOptionItemColorDataSet.yml");
//        SingleOptionItemDto singleOptionItemDto = SingleOptionItemDto.builder()
//                .optionGroup(OptionGroupDto.builder()
//                        .value("Color")
//                        .label("Color")
//                        .build())
//                .optionItem(OptionItemDto.builder()
//                        .value("Orange")
//                        .label("Orange")
//                        .build())
//                .build();
//        Map<String, String> requestParams = Map.of("optionGroup", "Color", "optionItem", "Yellow");
//
////        try {
////            mockMvc.perform(post(URL_TEMPLATE)
////                            .params(getMultiValueMap(requestParams))
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(objectMapper.writeValueAsString(singleOptionItemDto)))
////                    .andExpect(status().isOk());
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//
//        // verifyExpectedData("/datasets/e2e/item/articular_item/testUpdateE2EAllOptionItemColorDataSet.yml");
//    }
//
//    @Test
//    public void testUpdateOptionItemWithoutOptionGroupByGroup() {
//        // loadDataSet("/datasets/e2e/item/articular_item/testE2EAllOptionItemSizeDataSet.yml");
//        SingleOptionItemDto singleOptionItemDto = getUpdateSingleOptionItemSizeGroupDto();
//        Map<String, String> requestParams = Map.of("optionGroup", "Size", "optionItem", "XL");
//
////        try {
////            mockMvc.perform(post(URL_TEMPLATE)
////                            .params(getMultiValueMap(requestParams))
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(objectMapper.writeValueAsString(singleOptionItemDto)))
////                    .andExpect(status().isOk());
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//        // verifyExpectedData("/datasets/e2e/item/articular_item/testUpdateE2EAllOptionItemSizeDataSet.yml");
//    }
//
//    @Test
//    public void testDeleteOptionItemByArticularId() {
//        // loadDataSet("/datasets/e2e/item/articular_item/testE2EArticularIdOptionItemColorDataSet.yml");
//        Map<String, String> requestParams = Map.of("articularId", "1234", "optionValue", "Red");
//
////        try {
////            mockMvc.perform(delete(URL_TEMPLATE)
////                            .params(getMultiValueMap(requestParams))
////                            .contentType(MediaType.TEXT_PLAIN))
////                    .andExpect(status().isOk());
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//        // verifyExpectedData("/datasets/e2e/item/articular_item/testDeleteE2EAllOptionItemSizeDataSet.yml");
//    }
//
//    @Test
//    public void testDeleteOptionItemByOptionGroup() {
//        // loadDataSet("/datasets/e2e/item/articular_item/testE2EArticularIdOptionItemColorDataSet.yml");
//
//        Map<String, String> requestParams = Map.of("optionGroup", "Color", "optionValue", "Red");
//
////        try {
////            mockMvc.perform(delete(URL_TEMPLATE)
////                            .params(getMultiValueMap(requestParams))
////                            .contentType(MediaType.TEXT_PLAIN))
////                    .andExpect(status().isOk());
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//        // verifyExpectedData("/datasets/e2e/item/articular_item/testDeleteE2EAllOptionItemColorDataSet.yml");
//    }
//
//    @Test
//    public void testGetOptionItemListByArticularId() {
//        // loadDataSet("/datasets/e2e/item/articular_item/testE2EArticularIdOptionItemSizeDataSet.yml");
//        SingleOptionItemDto expectedDto = getSingleOptionItemSizeGroupDto();
//        Map<String, String> requestParams = Map.of("articularId", "1234");
//
////        MvcResult mvcResult;
////        try {
////            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
////                            .params(getMultiValueMap(requestParams))
////                            .contentType(MediaType.APPLICATION_JSON))
////                    .andExpect(status().isOk())
////                    .andReturn();
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//
////        try {
////            String jsonResponse = mvcResult.getResponse().getContentAsString();
////            List<OptionGroupOptionItemSetDto> list = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
////            list.forEach(optionItemDto -> {
////                assertEquals(expectedDto.getOptionGroup().getValue(), optionItemDto.getOptionGroup().getValue());
////                assertEquals(expectedDto.getOptionGroup().getLabel(), optionItemDto.getOptionGroup().getLabel());
////                assertEquals(expectedDto.getOptionItem().getValue(), optionItemDto.getOptionItems().stream().toList().get(0).getValue());
////                assertEquals(expectedDto.getOptionItem().getLabel(), optionItemDto.getOptionItems().stream().toList().get(0).getLabel());
////            });
////        } catch (JsonProcessingException | UnsupportedEncodingException e) {
////            throw new RuntimeException("Error processing the JSON response", e);
////        }
//    }
//
//    @Test
//    public void testGetOptionItemList() {
//        // loadDataSet("/datasets/e2e/item/articular_item/testE2EArticularIdOptionItemSizeDataSet.yml");
//        OptionGroupOptionItemSetDto expectedDto = OptionGroupOptionItemSetDto.builder()
//                .optionGroup(OptionGroupDto.builder()
//                        .label("Size")
//                        .value("Size")
//                        .build())
//                .optionItems(Set.of(OptionItemDto.builder()
//                        .value("M")
//                        .label("M")
//                        .build()))
//                .build();
//
////        MvcResult mvcResult;
////        try {
////            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
////                            .contentType(MediaType.APPLICATION_JSON))
////                    .andExpect(status().isOk())
////                    .andReturn();
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
////
////        try {
////            String jsonResponse = mvcResult.getResponse().getContentAsString();
////            List<OptionGroupOptionItemSetDto> list = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
////            list.forEach(optionItemDto -> {
////                assertEquals(expectedDto.getOptionGroup().getValue(), optionItemDto.getOptionGroup().getValue());
////                assertEquals(expectedDto.getOptionGroup().getLabel(), optionItemDto.getOptionGroup().getLabel());
////                assertEquals(expectedDto.getOptionItems(), optionItemDto.getOptionItems());
////            });
////        } catch (JsonProcessingException | UnsupportedEncodingException e) {
////            throw new RuntimeException("Error processing the JSON response", e);
////        }
//    }
//
//    @Test
//    public void testGetOptionItemListByGroup() {
//        // loadDataSet("/datasets/e2e/item/articular_item/testE2EAllOptionItemSizeDataSet.yml");
//        OptionGroupOptionItemSetDto expectedDto = getOptionItemSizeGroupDto();
//        Map<String, String> requestParams = Map.of("optionGroup", "Size");
//
////        MvcResult mvcResult;
////        try {
////            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/group")
////                            .params(getMultiValueMap(requestParams))
////                            .contentType(MediaType.APPLICATION_JSON))
////                    .andExpect(status().isOk())
////                    .andReturn();
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//
////        try {
////            String jsonResponse = mvcResult.getResponse().getContentAsString();
////            OptionGroupOptionItemSetDto actualDto = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
////            assertEquals(expectedDto.getOptionGroup().getValue(), actualDto.getOptionGroup().getValue());
////            assertEquals(expectedDto.getOptionGroup().getLabel(), actualDto.getOptionGroup().getLabel());
////            assertEquals(expectedDto.getOptionItems(), actualDto.getOptionItems());
////        } catch (JsonProcessingException | UnsupportedEncodingException e) {
////            throw new RuntimeException("Error processing the JSON response", e);
////        }
//
//    }
//
//    private OptionGroupOptionItemSetDto getOptionItemSizeGroupDto() {
//        return OptionGroupOptionItemSetDto.builder()
//                .optionGroup(getOptionGroupDto("Size"))
//                .optionItems(Set.of(getOptionItemDto("M"),
//                        getOptionItemDto("L"),
//                        getOptionItemDto("XL"),
//                        getOptionItemDto("S")))
//                .build();
//    }
//
//    private SingleOptionItemDto getSingleOptionItemSizeGroupDto() {
//        return SingleOptionItemDto.builder()
//                .optionGroup(getOptionGroupDto("Size"))
//                .optionItem(getOptionItemDto("M"))
//                .build();
//    }
//
//    private SingleOptionItemDto getUpdateSingleOptionItemSizeGroupDto() {
//        return SingleOptionItemDto.builder()
//                .optionGroup(getOptionGroupDto("USize"))
//                .optionItem(getOptionItemDto("UXL"))
//                .build();
//    }
//
//    private OptionGroupDto getOptionGroupDto(String value) {
//        return OptionGroupDto.builder()
//                .label(value)
//                .value(value)
//                .build();
//    }
//
//    private OptionItemDto getOptionItemDto(String value) {
//        return OptionItemDto.builder()
//                .label(value)
//                .value(value)
//                .build();
//    }
//
//    private OptionGroupOptionItemSetDto getOptionItemColorGroupDto() {
//        return OptionGroupOptionItemSetDto.builder()
//                .optionGroup(getOptionGroupDto("Color"))
//                .optionItems(
//                        Set.of(getOptionItemDto("Red"),
//                                getOptionItemDto("Blue"),
//                                getOptionItemDto("Yellow")))
//                .build();
//    }
//}
