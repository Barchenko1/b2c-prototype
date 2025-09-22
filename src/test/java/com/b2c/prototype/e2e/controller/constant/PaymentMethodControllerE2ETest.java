
package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
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

public class PaymentMethodControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/order/paymentmethod";

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/emptyE2EPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/payment_method/updateE2EPaymentMethodDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Cash")
                .value("Update Cash")
                .build();
        try {
            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("value", "Cash")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/payment_method/updateE2EPaymentMethodDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Cash")
                .value("Update Cash")
                .build();

        try {
            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("value", "Cash")
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/payment_method/emptyE2EPaymentMethodDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .param("value", "Cash"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<PaymentMethod> constantPayloadDtoList = List.of(
                PaymentMethod.builder()
                        .label("Card")
                        .value("Card")
                        .build(),
                PaymentMethod.builder()
                        .label("Cash")
                        .value("Cash")
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
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        ConstantPayloadDto dto = getConstantPayloadDto();
        try {

            MvcResult mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .param("value", "Cash"))
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
                .label("Cash")
                .value("Cash")
                .build();
    }

}
