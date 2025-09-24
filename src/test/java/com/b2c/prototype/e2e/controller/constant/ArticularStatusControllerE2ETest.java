package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.ArticularStatusDto;
import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArticularStatusControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/item/articular/status";

    @Test
    @DataSet(value = "datasets/e2e/item/articular_status/emptyE2EArticularStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_status/testE2EArticularStatusDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/item/articular_status/testE2EArticularStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_status/updateE2EArticularStatusDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Test1")
                .value("Update Test1")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Test1")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_status/testE2EArticularStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_status/updateE2EArticularStatusDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Test1")
                .value("Update Test1")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Test1")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_status/testE2EArticularStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_status/emptyE2EArticularStatusDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Test2")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_status/testE2EArticularStatusDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<ArticularStatusDto> constantPayloadDtoList = List.of(
                ArticularStatusDto.builder()
                        .label("Test1")
                        .value("Test1")
                        .build(),
                ArticularStatusDto.builder()
                        .label("Test2")
                        .value("Test2")
                        .build());

        List<ArticularStatusDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<ArticularStatusDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_status/testE2EArticularStatusDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        AvailabilityStatusDto expected = AvailabilityStatusDto.builder()
                .label("Test2")
                .value("Test2")
                .build();

        AvailabilityStatusDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Test2")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(AvailabilityStatusDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .label("Test2")
                .value("Test2")
                .build();
    }



}
