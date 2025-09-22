package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BrandControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/item/brand";

    @Test
    @DataSet(value = "datasets/e2e/item/brand/emptyE2EBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/brand/testE2EBrandDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        ConstantPayloadDto dto = getConstantPayloadDto();
        try {
            String jsonPayload = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/item/brand/testE2EBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/brand/updateE2EBrandDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Apple")
                .value("Update Apple")
                .build();
        try {
            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("value", "Apple")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/item/brand/testE2EBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/brand/updateE2EBrandDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Apple")
                .value("Update Apple")
                .build();

        try {
            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("value", "Apple")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/item/brand/testE2EBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/brand/emptyE2EBrandDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .param("value", "Apple"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/item/brand/testE2EBrandDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<Brand> constantPayloadDtoList = List.of(
                Brand.builder()
                        .label("Android")
                        .value("Android")
                        .build(),
                Brand.builder()
                        .label("Apple")
                        .value("Apple")
                        .build());
        try {

            MvcResult mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/item/brand/testE2EBrandDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        ConstantPayloadDto dto = getConstantPayloadDto();
        try {

            MvcResult mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .param("value", "Apple"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            assertMvcResult(mvcResult, dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .label("Apple")
                .value("Apple")
                .build();
    }

}
