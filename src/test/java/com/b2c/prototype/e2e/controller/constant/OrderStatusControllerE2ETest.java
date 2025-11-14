package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.order.OrderStatusDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderStatusControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/order/status";

    @Test
    @DataSet(value = "datasets/e2e/order/order_status/emptyE2EOrderStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/order_status/testE2EOrderStatusDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/order/order_status/testE2EOrderStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/order_status/updateE2EOrderStatusDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update Complete")
                .key("Update Complete")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Complete")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/order_status/testE2EOrderStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/order_status/emptyE2EOrderStatusDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Complete")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/order_status/testE2EOrderStatusDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<OrderStatusDto> constantPayloadDtoList = List.of(
                OrderStatusDto.builder()
                        .value("Pending")
                        .key("Pending")
                        .build(),
                OrderStatusDto.builder()
                        .value("Complete")
                        .key("Complete")
                        .build());

        List<OrderStatusDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<OrderStatusDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/order_status/testE2EOrderStatusDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        OrderStatusDto expected = OrderStatusDto.builder()
                .value("Complete")
                .key("Complete")
                .build();
        OrderStatusDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Complete")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(OrderStatusDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .value("Complete")
                .key("Complete")
                .build();
    }

}
