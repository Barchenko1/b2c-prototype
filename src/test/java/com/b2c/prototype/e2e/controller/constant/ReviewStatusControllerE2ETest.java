package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.ReviewStatusDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReviewStatusControllerE2ETest extends BasicE2ETest {
    private final String URL_TEMPLATE = "/api/v1/item/review/status";

    @Test
    @DataSet(value = "datasets/e2e/item/review_status/emptyE2EReviewStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/review_status/testE2EReviewStatusDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/item/review_status/testE2EReviewStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/review_status/updateE2EReviewStatusDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update PENDING")
                .key("Update PENDING")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "PENDING")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/review_status/testE2EReviewStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/review_status/emptyE2EReviewStatusDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "DONE")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/review_status/testE2EReviewStatusDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<ReviewStatusDto> constantPayloadDtoList = List.of(
                ReviewStatusDto.builder()
                        .value("PENDING")
                        .key("PENDING")
                        .build(),
                ReviewStatusDto.builder()
                        .value("DONE")
                        .key("DONE")
                        .build());

        List<ReviewStatusDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<ReviewStatusDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/review_status/testE2EReviewStatusDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        ReviewStatusDto expected = ReviewStatusDto.builder()
                .value("DONE")
                .key("DONE")
                .build();

        ReviewStatusDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "DONE")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(ReviewStatusDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .value("DONE")
                .key("DONE")
                .build();
    }
}
