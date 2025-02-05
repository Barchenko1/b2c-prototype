package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OptionItemControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/option";

    @BeforeEach
    public void setup() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM articular_item_option_item");
            statement.execute("DELETE FROM articular_item");
            statement.execute("DELETE FROM option_item");
            statement.execute("DELETE FROM option_group");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    @Test
    public void testSaveOptionItemByArticularId() {
        loadDataSet("/datasets/option/option_item/emptyE2EArticularIdOptionItemWithOptionGroupDataSet.yml");
        Map<String, String> requestParams = Map.of("articularId", "1234");
        SingleOptionItemDto singleOptionItemDto = getSingleOptionItemSizeGroupDto();

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(singleOptionItemDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/option/option_item/testE2EArticularIdOptionItemSizeDataSet.yml");
    }

    @Test
    public void testSaveOptionItemByArticularIdWithoutOptionGroup() {
        loadDataSet("/datasets/option/option_item/emptyE2EArticularIdOptionItemWithoutOptionGroupDataSet.yml");
        Map<String, String> requestParams = Map.of("articularId", "1234");
        SingleOptionItemDto singleOptionItemDto = getSingleOptionItemSizeGroupDto();

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(singleOptionItemDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/option/option_item/testE2EArticularIdOptionItemSizeDataSet.yml");
    }

    @Test
    public void testSaveOptionItemByOptionGroup() {
        loadDataSet("/datasets/option/option_item/emptyE2EOptionItemWithOptionGroupDataSet.yml");
        OptionItemDto optionItemDto = getOptionItemSizeGroupDto();
        List<OptionItemDto> optionItemDtoList = List.of(optionItemDto);

        try {
            mockMvc.perform(post(URL_TEMPLATE + "/group")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(optionItemDtoList)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/option/option_item/testE2EAllOptionItemSizeDataSet.yml");
    }

    @Test
    public void testSaveOptionItemByOptionGroupWithOutOptionGroup() {
        loadDataSet("/datasets/option/option_item/emptyE2EOptionItemWithoutOptionGroupDataSet.yml");
        OptionItemDto optionItemDto = getOptionItemColorGroupDto();
        List<OptionItemDto> optionItemDtoList = List.of(optionItemDto);

        try {
            mockMvc.perform(post(URL_TEMPLATE + "/group")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(optionItemDtoList)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/option/option_item/testE2EAllOptionItemColorDataSet.yml");
    }

    @Test
    public void testUpdateOptionItemByGroup() {
        loadDataSet("/datasets/option/option_item/testE2EAllOptionItemColorDataSet.yml");
        SingleOptionItemDto singleOptionItemDto = SingleOptionItemDto.builder()
                .optionGroup(ConstantPayloadDto.builder()
                        .value("Color")
                        .label("Color")
                        .build())
                .optionItem(ConstantPayloadDto.builder()
                        .value("Orange")
                        .label("Orange")
                        .build())
                .build();
        Map<String, String> requestParams = Map.of("optionGroup", "Color", "optionItem", "Yellow");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(singleOptionItemDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/option/option_item/testUpdateE2EAllOptionItemColorDataSet.yml");
    }

    @Test
    public void testUpdateOptionItemWithoutOptionGroupByGroup() {
        loadDataSet("/datasets/option/option_item/testE2EAllOptionItemSizeDataSet.yml");
        SingleOptionItemDto singleOptionItemDto = getUpdateSingleOptionItemSizeGroupDto();
        Map<String, String> requestParams = Map.of("optionGroup", "Size", "optionItem", "XL");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(singleOptionItemDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/option/option_item/testUpdateE2EAllOptionItemSizeDataSet.yml");
    }

    @Test
    public void testDeleteOptionItemByArticularId() {
        loadDataSet("/datasets/option/option_item/testE2EArticularIdOptionItemColorDataSet.yml");
        Map<String, String> requestParams = Map.of("articularId", "1234", "optionValue", "Red");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.TEXT_PLAIN))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/option/option_item/testDeleteE2EAllOptionItemSizeDataSet.yml");
    }

    @Test
    public void testDeleteOptionItemByOptionGroup() {
        loadDataSet("/datasets/option/option_item/testE2EArticularIdOptionItemColorDataSet.yml");

        Map<String, String> requestParams = Map.of("optionGroup", "Color", "optionValue", "Red");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.TEXT_PLAIN))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        verifyExpectedData("/datasets/option/option_item/testDeleteE2EAllOptionItemColorDataSet.yml");
    }

    @Test
    public void testGetOptionItemListByArticularId() {
        loadDataSet("/datasets/option/option_item/testE2EArticularIdOptionItemSizeDataSet.yml");
        SingleOptionItemDto expectedDto = getSingleOptionItemSizeGroupDto();
        Map<String, String> requestParams = Map.of("articularId", "1234");

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
            List<OptionItemDto> list = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            list.forEach(optionItemDto -> {
                assertEquals(expectedDto.getOptionGroup().getValue(), optionItemDto.getOptionGroup().getValue());
                assertEquals(expectedDto.getOptionGroup().getLabel(), optionItemDto.getOptionGroup().getLabel());
                assertEquals(expectedDto.getOptionItem().getValue(), optionItemDto.getOptionItems().stream().toList().get(0).getValue());
                assertEquals(expectedDto.getOptionItem().getLabel(), optionItemDto.getOptionItems().stream().toList().get(0).getLabel());
            });
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    @Test
    public void testGetOptionItemList() {
        loadDataSet("/datasets/option/option_item/testE2EArticularIdOptionItemSizeDataSet.yml");
        SingleOptionItemDto expectedDto = getSingleOptionItemSizeGroupDto();

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
            List<OptionItemDto> list = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            list.forEach(optionItemDto -> {
                assertEquals(expectedDto.getOptionGroup().getValue(), optionItemDto.getOptionGroup().getValue());
                assertEquals(expectedDto.getOptionGroup().getLabel(), optionItemDto.getOptionGroup().getLabel());
                assertEquals(expectedDto.getOptionItem().getValue(), optionItemDto.getOptionItems().stream().toList().get(0).getValue());
                assertEquals(expectedDto.getOptionItem().getLabel(), optionItemDto.getOptionItems().stream().toList().get(0).getLabel());
            });
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    @Test
    public void testGetOptionItemListByGroup() {
        loadDataSet("/datasets/option/option_item/testE2EAllOptionItemSizeDataSet.yml");
        SingleOptionItemDto expectedDto = getSingleOptionItemSizeGroupDto();
        Map<String, String> requestParams = Map.of("optionGroup", "Size");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/group")
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            OptionItemDto actualDto = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            assertEquals(expectedDto.getOptionGroup().getValue(), actualDto.getOptionGroup().getValue());
            assertEquals(expectedDto.getOptionGroup().getLabel(), actualDto.getOptionGroup().getLabel());
            assertEquals(expectedDto.getOptionItem().getValue(), actualDto.getOptionItems().stream().toList().get(0).getValue());
            assertEquals(expectedDto.getOptionItem().getLabel(), actualDto.getOptionItems().stream().toList().get(0).getLabel());
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }

    }

    private MultiValueMap<String, String> getMultiValueMap(Map<String, String> requestParams) {
        MultiValueMap<String, String> multiValueMap =  new LinkedMultiValueMap<>();
        requestParams.forEach(multiValueMap::add);
        return multiValueMap;
    }

    private OptionItemDto getOptionItemSizeGroupDto() {
        return OptionItemDto.builder()
                .optionGroup(getConstantPayloadDto("Size"))
                .optionItems(Set.of(getConstantPayloadDto("XL"),
                                getConstantPayloadDto("L"),
                                getConstantPayloadDto("M"),
                                getConstantPayloadDto("S")))
                .build();
    }

    private SingleOptionItemDto getSingleOptionItemSizeGroupDto() {
        return SingleOptionItemDto.builder()
                .optionGroup(getConstantPayloadDto("Size"))
                .optionItem(getConstantPayloadDto("M"))
                .build();
    }

    private SingleOptionItemDto getUpdateSingleOptionItemSizeGroupDto() {
        return SingleOptionItemDto.builder()
                .optionGroup(getConstantPayloadDto("USize"))
                .optionItem(getConstantPayloadDto("UXL"))
                .build();
    }

    private ConstantPayloadDto getConstantPayloadDto(String value) {
        return ConstantPayloadDto.builder()
                .label(value)
                .value(value)
                .build();
    }

    private OptionItemDto getOptionItemColorGroupDto() {
        return OptionItemDto.builder()
                .optionGroup(getConstantPayloadDto("Color"))
                .optionItems(
                        Set.of(getConstantPayloadDto("Red"),
                        getConstantPayloadDto("Blue"),
                        getConstantPayloadDto("Yellow")))
                .build();
    }

    private OptionItemDto getOneOptionItemColorGroupDto() {
        return OptionItemDto.builder()
                .optionGroup(getConstantPayloadDto("Color"))
                .optionItems(
                        Set.of(getConstantPayloadDto("Red")))
                .build();
    }
}
