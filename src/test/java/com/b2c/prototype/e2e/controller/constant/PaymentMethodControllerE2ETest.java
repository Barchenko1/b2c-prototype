
package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.PAYMENT_METHOD_SERVICE_ID;

public class PaymentMethodControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Card")
                .value("Card")
                .build();

        postConstantEntity(constantPayloadDto,
                PAYMENT_METHOD_SERVICE_ID,
                "/datasets/dao/payment/payment_method/emptyPaymentMethodDataSet.yml",
                "/datasets/dao/payment/payment_method/savePaymentMethodDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Card")
                .value("Update Card")
                .build();

        putConstantEntity(constantPayloadDto,
                PAYMENT_METHOD_SERVICE_ID,
                "Card",
                "/datasets/dao/payment/payment_method/testPaymentMethodDataSet.yml",
                "/datasets/dao/payment/payment_method/updatePaymentMethodDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Card")
                .value("Update Card")
                .build();

        patchConstantEntity(constantPayloadDto,
                PAYMENT_METHOD_SERVICE_ID,
                "Card",
                "/datasets/dao/payment/payment_method/testPaymentMethodDataSet.yml",
                "/datasets/dao/payment/payment_method/updatePaymentMethodDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                PAYMENT_METHOD_SERVICE_ID,
                "Card",
                "/datasets/dao/payment/payment_method/testPaymentMethodDataSet.yml",
                "/datasets/dao/payment/payment_method/emptyPaymentMethodDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("Card")
                        .value("Card")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("Cash")
                        .value("Cash")
                        .build());

        MvcResult mvcResult = getConstantEntities(PAYMENT_METHOD_SERVICE_ID,
                "/datasets/e2e/payment/payment_method/testAllPaymentMethodDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto entity = ConstantPayloadDto.builder()
                .label("Card")
                .value("Card")
                .build();

        MvcResult mvcResult = getConstantEntity(PAYMENT_METHOD_SERVICE_ID,
                "Card",
                "/datasets/dao/payment/payment_method/testPaymentMethodDataSet.yml");
        assertMvcResult(mvcResult, entity);
    }



}
