package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.RatingDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RatingControllerE2ETest extends BasicE2ETest {
    private final String URL_TEMPLATE = "/api/v1/item/review/rating";

    @Test
    @DataSet(value = "datasets/e2e/item/rating/emptyE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        NumberConstantPayloadDto dto = getConstantPayloadDto();
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
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/updateE2ERatingDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        NumberConstantPayloadDto constantPayloadDto = NumberConstantPayloadDto.builder()
                .value(3)
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "2")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/updateE2ERatingDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        NumberConstantPayloadDto constantPayloadDto = NumberConstantPayloadDto.builder()
                .value(3)
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "2")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/rating/emptyE2ERatingDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "2")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<RatingDto> constantPayloadDtoList = List.of(
                RatingDto.builder()
                        .value(1)
                        .build(),
                RatingDto.builder()
                        .value(2)
                        .build());

        List<RatingDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<RatingDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/rating/testE2ERatingDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        RatingDto expected = RatingDto.builder()
                .value(2)
                .build();
        RatingDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "2")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(RatingDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    private NumberConstantPayloadDto getConstantPayloadDto() {
        return NumberConstantPayloadDto.builder()
                .value(2)
                .build();
    }
}
