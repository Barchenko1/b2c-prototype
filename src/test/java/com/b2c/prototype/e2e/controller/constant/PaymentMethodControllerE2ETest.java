
package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.order.PaymentMethodDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PaymentMethodControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/order/payment/method";

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/emptyE2EPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        ConstantPayloadDto dto = getConstantPayloadDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/payment_method/updateE2EPaymentMethodDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update Cash")
                .key("Update Cash")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Cash")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/payment_method/updateE2EPaymentMethodDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update Cash")
                .key("Update Cash")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Cash")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/payment_method/emptyE2EPaymentMethodDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Cash")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<PaymentMethodDto> constantPayloadDtoList = List.of(
                PaymentMethodDto.builder()
                        .value("Card")
                        .key("Card")
                        .build(),
                PaymentMethodDto.builder()
                        .value("Cash")
                        .key("Cash")
                        .build());

        List<PaymentMethodDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<PaymentMethodDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/payment_method/testE2EPaymentMethodDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        PaymentMethodDto expected = PaymentMethodDto.builder()
                .value("Card")
                .key("Card")
                .build();

        PaymentMethodDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Card")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(PaymentMethodDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .value("Cash")
                .key("Cash")
                .build();
    }

}
