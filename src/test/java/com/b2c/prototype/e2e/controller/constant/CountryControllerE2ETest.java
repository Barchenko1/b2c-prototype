package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.COUNTRY_SERVICE_ID;

public class CountryControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        CountryDto countryDto = CountryDto.builder()
                .label("USA")
                .value("USA")
                .flagImagePath("/images/usa.jpg")
                .build();

        postConstantEntity(countryDto,
                COUNTRY_SERVICE_ID,
                "/datasets/dao/country/emptyCountryDataSet.yml",
                "/datasets/dao/country/saveCountryDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        CountryDto countryDto = CountryDto.builder()
                .label("USA")
                .value("Update USA")
                .flagImagePath("/images/usa.jpg")
                .build();

        putConstantEntity(countryDto,
                COUNTRY_SERVICE_ID,
                "USA",
                "/datasets/dao/country/testCountryDataSet.yml",
                "/datasets/dao/country/updateCountryDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        CountryDto countryDto = CountryDto.builder()
                .label("USA")
                .value("Update USA")
                .flagImagePath("/images/usa.jpg")
                .build();

        patchConstantEntity(countryDto,
                COUNTRY_SERVICE_ID,
                "USA",
                "/datasets/dao/country/testCountryDataSet.yml",
                "/datasets/dao/country/updateCountryDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                COUNTRY_SERVICE_ID,
                "USA",
                "/datasets/dao/country/testCountryDataSet.yml",
                "/datasets/dao/country/emptyCountryDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<CountryDto> countryDtoList = List.of(
                CountryDto.builder()
                        .label("USA")
                        .value("USA")
                        .flagImagePath("/images/usa.jpg")
                        .build(),
                CountryDto.builder()
                        .label("CANADA")
                        .value("CANADA")
                        .flagImagePath("/images/canada.jpg")
                        .build()
        );

        MvcResult mvcResult = getConstantEntities(COUNTRY_SERVICE_ID,
                "/datasets/e2e/country/testAllCountryDataSet.yml");

        assertMvcListResult(mvcResult, countryDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        CountryDto countryDto = getCountryDto();
        MvcResult mvcResult = getConstantEntity(
                COUNTRY_SERVICE_ID,
                "USA",
                "/datasets/dao/country/testCountryDataSet.yml");

        assertMvcResult(mvcResult, countryDto);
    }

    private CountryDto getCountryDto() {
        return CountryDto.builder()
                .label("USA")
                .value("USA")
                .build();
    }



}
