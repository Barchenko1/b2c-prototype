package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.b2c.prototype.util.Constant.BRAND_SERVICE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BrandControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/constant";

    @Test
    @DataSet(value = "datasets/dao/item/brand/emptyBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/brand/saveBrandDataSet.yml", orderBy = "id")
    public void testCreateBrand() {
        ConstantPayloadDto dto = getConstantPayloadDto();
        try {
            String jsonPayload = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("serviceId", BRAND_SERVICE_ID)
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        postConstantEntity(dto,
//                BRAND_SERVICE_ID,
//                "/datasets/dao/item/brand/emptyBrandDataSet.yml",
//                "/datasets/dao/item/brand/saveBrandDataSet.yml");
    }

    @Test
    @DataSet(value = "datasets/dao/item/brand/testBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/brand/updateBrandDataSet.yml", orderBy = "id")
    public void testUpdateBrand() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Apple")
                .value("Update Apple")
                .build();
        try {
            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("serviceId", BRAND_SERVICE_ID)
                            .param("value", "Apple")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        putConstantEntity(constantPayloadDto,
//                BRAND_SERVICE_ID,
//                "Apple",
//                "/datasets/dao/item/brand/testBrandDataSet.yml",
//                "/datasets/dao/item/brand/updateBrandDataSet.yml");
    }

    @Test
    @DataSet(value = "datasets/dao/item/brand/testBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/brand/updateBrandDataSet.yml", orderBy = "id")
    public void testPatchBrand() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Apple")
                .value("Update Apple")
                .build();

        try {
            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("serviceId", BRAND_SERVICE_ID)
                            .param("value", "Apple")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        patchConstantEntity(constantPayloadDto,
//                BRAND_SERVICE_ID,
//                "Apple",
//                "/datasets/dao/item/brand/testBrandDataSet.yml",
//                "/datasets/dao/item/brand/updateBrandDataSet.yml");
    }

    @Test
    @DataSet(value = "datasets/dao/item/brand/testBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/brand/emptyBrandDataSet.yml", orderBy = "id")
    public void testDeleteBrand() {
        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .header("serviceId", BRAND_SERVICE_ID)
                            .param("value", "Apple"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        deleteConstantEntity(
//                BRAND_SERVICE_ID,
//                "Apple",
//                "/datasets/dao/item/brand/testBrandDataSet.yml",
//                "/datasets/dao/item/brand/emptyBrandDataSet.yml");
    }

    @Test
    @DataSet(value = "datasets/dao/item/brand/testBrandDataSet.yml", cleanBefore = true)
    public void testGetBrands() {
        List<Brand> constantPayloadDtoList = List.of(
                Brand.builder()
                        .label("Apple")
                        .value("Apple")
                        .build(),
                Brand.builder()
                        .label("Android")
                        .value("Android")
                        .build());
        try {

            MvcResult mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .header("serviceId", BRAND_SERVICE_ID)
                            .param("value", "Apple"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        MvcResult mvcResult = getConstantEntities(BRAND_SERVICE_ID,
//                "/datasets/e2e/item/brand/testAllBrandDataSet.yml");


    }

    @Test
    @DataSet(value = "datasets/dao/item/brand/testBrandDataSet.yml", cleanBefore = true)
    public void testGetBrand() {
        ConstantPayloadDto dto = getConstantPayloadDto();
        try {

            MvcResult mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .header("serviceId", BRAND_SERVICE_ID)
                            .param("value", "Apple"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            assertMvcResult(mvcResult, dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        MvcResult mvcResult = getConstantEntity(BRAND_SERVICE_ID,
//                "Apple",
//                "/datasets/dao/item/brand/testBrandDataSet.yml");

    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .label("Apple")
                .value("Apple")
                .build();
    }

    protected <T> void assertMvcResult(MvcResult mvcResult, T expected) {
        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            T actual = (T) objectMapper.readValue(jsonResponse, expected.getClass());
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    protected <T> void assertMvcListResult(MvcResult mvcResult, List<T> expectedList, TypeReference<List<T>> typeReference) {
        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<T> actualList = objectMapper.readValue(jsonResponse, typeReference);
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

}
