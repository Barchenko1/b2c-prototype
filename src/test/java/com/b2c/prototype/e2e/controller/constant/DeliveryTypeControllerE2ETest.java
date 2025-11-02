package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.order.DeliveryTypeDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeliveryTypeControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/order/delivery/type";

    @Test
    @DataSet(value = "datasets/e2e/order/delivery_type/emptyE2EDeliveryTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/delivery_type/testE2EDeliveryTypeDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        ConstantPayloadDto dto = getConstantPayloadDto();
        try {
            String jsonPayload = objectMapper.writeValueAsString(dto);

            webTestClient.post()
                    .uri(URL_TEMPLATE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonPayload)
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DataSet(value = "datasets/e2e/order/delivery_type/testE2EDeliveryTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/delivery_type/updateE2EDeliveryTypeDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update Courier")
                .key("Update Courier")
                .build();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Courier")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/delivery_type/testE2EDeliveryTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/delivery_type/updateE2EDeliveryTypeDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update Courier")
                .key("Update Courier")
                .build();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Courier")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/delivery_type/testE2EDeliveryTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/delivery_type/emptyE2EDeliveryTypeDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Courier")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/delivery_type/testE2EDeliveryTypeDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<DeliveryTypeDto> constantPayloadDtoList = List.of(
                DeliveryTypeDto.builder()
                        .value("Post")
                        .key("Post")
                        .build(),
                DeliveryTypeDto.builder()
                        .value("Courier")
                        .key("Courier")
                        .build());

        List<DeliveryTypeDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<DeliveryTypeDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/delivery_type/testE2EDeliveryTypeDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        DeliveryTypeDto expected = DeliveryTypeDto.builder()
                .value("Courier")
                .key("Courier")
                .build();

        DeliveryTypeDto actual =
                webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(URL_TEMPLATE)
                                .queryParam("key", "Courier")
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(DeliveryTypeDto.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .value("Courier")
                .key("Courier")
                .build();
    }
}
