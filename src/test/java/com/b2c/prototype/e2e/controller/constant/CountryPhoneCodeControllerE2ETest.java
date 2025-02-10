package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.COUNTRY_PHONE_CODE_SERVICE_ID;

public class CountryPhoneCodeControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("+48")
                .value("+48")
                .build();

        postConstantEntity(constantPayloadDto,
                COUNTRY_PHONE_CODE_SERVICE_ID,
                "/datasets/user/country_phone_code/emptyCountryPhoneCodeDataSet.yml",
                "/datasets/user/country_phone_code/saveCountryPhoneCodeDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Update +48")
                .value("Update +48")
                .build();

        putConstantEntity(constantPayloadDto,
                COUNTRY_PHONE_CODE_SERVICE_ID,
                "+48",
                "/datasets/user/country_phone_code/testCountryPhoneCodeDataSet.yml",
                "/datasets/user/country_phone_code/updateCountryPhoneCodeDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Update +48")
                .value("Update +48")
                .build();

        patchConstantEntity(constantPayloadDto,
                COUNTRY_PHONE_CODE_SERVICE_ID,
                "+48",
                "/datasets/user/country_phone_code/testCountryPhoneCodeDataSet.yml",
                "/datasets/user/country_phone_code/updateCountryPhoneCodeDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                COUNTRY_PHONE_CODE_SERVICE_ID,
                "+48",
                "/datasets/user/country_phone_code/testCountryPhoneCodeDataSet.yml",
                "/datasets/user/country_phone_code/emptyCountryPhoneCodeDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("+48")
                        .value("+48")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("+11")
                        .value("+11")
                        .build());

        MvcResult mvcResult = getConstantEntities(
                COUNTRY_PHONE_CODE_SERVICE_ID,
                "/datasets/user/country_phone_code/testAllCountryPhoneCodeDataSet.yml");

        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("+48")
                .value("+48")
                .build();

        MvcResult mvcResult = getConstantEntity(COUNTRY_PHONE_CODE_SERVICE_ID,
                "+48",
                "/datasets/user/country_phone_code/testCountryPhoneCodeDataSet.yml");

        assertMvcResult(mvcResult, dto);
    }

}
