package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TimeDurationOptionControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/order/option/timeduration";

    @Test
    @DataSet(value = "datasets/e2e/item/rating/emptyE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", orderBy = "id")
    public void testSaveEntity() {
        TimeDurationOptionDto dto = getTimeDurationOptionDto();
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
    @DataSet(value = "datasets/e2e/item/rating/emptyE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("12-14")
                .value("Update 12-14")
                .build();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "12-14")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testPatchTEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("12-14")
                .value("Update 12-14")
                .build();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "12-14")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/rating/emptyE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "12-14")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/rating/emptyE2ERatingDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        TimeDurationOptionDto expected = getTimeDurationOptionDto();
        TimeDurationOptionDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "ZoneB")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(TimeDurationOptionDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGetEntities() {
        List<TimeDurationOptionDto > constantPayloadDtoList = List.of(
                TimeDurationOptionDto.builder()
                        .label("9-11")
                        .value("9-11")
                        .build(),
                TimeDurationOptionDto.builder()
                        .label("12-14")
                        .value("12-14")
                        .build());

        List<TimeDurationOptionDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<TimeDurationOptionDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    private TimeDurationOptionDto getTimeDurationOptionDto() {
        return TimeDurationOptionDto.builder()
                .label("12-14")
                .value("12-14")
                .duration(120)
                .price(PriceDto.builder()
                        .amount(10)
                        .currency("USD")
                        .build())
                .build();
    }

}
