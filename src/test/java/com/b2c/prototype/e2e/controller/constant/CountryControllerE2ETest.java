package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.entity.address.Country;
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

public class CountryControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/order/delivery/country";

    @Test
    @DataSet(value = "datasets/e2e/order/country/emptyE2ECountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        CountryDto dto = getCountryDto();
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
    @DataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/country/updateE2ECountryDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        CountryDto countryDto = CountryDto.builder()
                .label("USA")
                .value("Update USA")
                .flagImagePath("/images/usa.jpg")
                .build();
        try {
            String jsonPayload = objectMapper.writeValueAsString(countryDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("value", "USA")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/country/updateE2ECountryDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        CountryDto countryDto = CountryDto.builder()
                .label("USA")
                .value("Update USA")
                .flagImagePath("/images/usa.jpg")
                .build();

        try {
            String jsonPayload = objectMapper.writeValueAsString(countryDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("value", "USA")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/country/emptyE2ECountryDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .param("value", "USA"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<Country> constantPayloadDtoList = List.of(
                Country.builder()
                        .label("CANADA")
                        .value("CANADA")
                        .flagImagePath("/images/canada.jpg")
                        .build(),
                Country.builder()
                        .label("USA")
                        .value("USA")
                        .flagImagePath("/images/usa.jpg")
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
    @DataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        CountryDto dto = getCountryDto();
        try {

            MvcResult mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .param("value", "USA"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            assertMvcResult(mvcResult, dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CountryDto getCountryDto() {
        return CountryDto.builder()
                .label("USA")
                .value("USA")
                .flagImagePath("/images/usa.jpg")
                .build();
    }

}
