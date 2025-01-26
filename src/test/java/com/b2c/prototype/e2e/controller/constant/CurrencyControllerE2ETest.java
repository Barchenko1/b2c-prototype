package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.CURRENCY_SERVICE_ID;

public class CurrencyControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("USD")
                .value("USD")
                .build();

        postConstantEntity(constantPayloadDto,
                CURRENCY_SERVICE_ID,
                "/datasets/price/currency/emptyCurrencyDataSet.yml",
                "/datasets/price/currency/saveCurrencyDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("USD")
                .value("Update USD")
                .build();

        putConstantEntity(constantPayloadDto,
                CURRENCY_SERVICE_ID,
                "USD",
                "/datasets/price/currency/testCurrencyDataSet.yml",
                "/datasets/price/currency/updateCurrencyDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("USD")
                .value("Update USD")
                .build();

        patchConstantEntity(constantPayloadDto,
                CURRENCY_SERVICE_ID,
                "USD",
                "/datasets/price/currency/testCurrencyDataSet.yml",
                "/datasets/price/currency/updateCurrencyDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                CURRENCY_SERVICE_ID,
                "USD",
                "/datasets/price/currency/testCurrencyDataSet.yml",
                "/datasets/price/currency/emptyCurrencyDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("USD")
                        .value("USD")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("EUR")
                        .value("EUR")
                        .build());

        MvcResult mvcResult = getConstantEntities(CURRENCY_SERVICE_ID,
                "/datasets/price/currency/testAllCurrencyDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("USD")
                .value("USD")
                .build();

        MvcResult mvcResult = getConstantEntity(CURRENCY_SERVICE_ID,
                "USD",
                "/datasets/price/currency/testCurrencyDataSet.yml");
        assertMvcResult(mvcResult, dto);
    }

}
