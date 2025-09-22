package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.review.Rating;
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

public class RatingControllerE2ETest extends BasicE2ETest {
    private final String URL_TEMPLATE = "/api/v1/item/review/rating";

    @Test
    @DataSet(value = "datasets/e2e/item/rating/emptyE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        NumberConstantPayloadDto dto = getConstantPayloadDto();
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
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/updateE2ERatingDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        NumberConstantPayloadDto constantPayloadDto = NumberConstantPayloadDto.builder()
                .value(3)
                .build();
        try {
            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("value", "2")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/updateE2ERatingDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        NumberConstantPayloadDto constantPayloadDto = NumberConstantPayloadDto.builder()
                .value(3)
                .build();

        try {
            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("value", "2")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/emptyE2ERatingDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .param("value", "2"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<Rating> constantPayloadDtoList = List.of(
                Rating.builder()
                        .value(1)
                        .build(),
                Rating.builder()
                        .value(2)
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
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        NumberConstantPayloadDto dto = getConstantPayloadDto();
        try {

            MvcResult mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .param("value", "2"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            assertMvcResult(mvcResult, dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private NumberConstantPayloadDto getConstantPayloadDto() {
        return NumberConstantPayloadDto.builder()
                .value(2)
                .build();
    }
}
