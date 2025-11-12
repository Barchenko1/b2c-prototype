package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AvailabilityStatusControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/store/stock/status";

    @Test
    @DataSet(value = "datasets/e2e/store/availability_status/emptyE2EAvailabilityStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/availability_status/testE2EAvailabilityStatusDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/store/availability_status/testE2EAvailabilityStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/availability_status/updateE2EAvailabilityStatusDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update Available")
                .key("Update Available")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Available")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/store/availability_status/testE2EAvailabilityStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/availability_status/updateE2EAvailabilityStatusDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update Available")
                .key("Update Available")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Available")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/store/availability_status/testE2EAvailabilityStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/availability_status/emptyE2EAvailabilityStatusDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Unavailable")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/store/availability_status/testE2EAvailabilityStatusDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<AvailabilityStatusDto> constantPayloadDtoList = List.of(
                AvailabilityStatusDto.builder()
                        .value("Available")
                        .key("Available")
                        .build(),
                AvailabilityStatusDto.builder()
                        .value("Unavailable")
                        .key("Unavailable")
                        .build());

        List<AvailabilityStatusDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<AvailabilityStatusDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/store/availability_status/testE2EAvailabilityStatusDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        AvailabilityStatusDto expected = AvailabilityStatusDto.builder()
                .value("Unavailable")
                .key("Unavailable")
                .build();

        AvailabilityStatusDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Unavailable")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(AvailabilityStatusDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .value("Unavailable")
                .key("Unavailable")
                .build();
    }
}
