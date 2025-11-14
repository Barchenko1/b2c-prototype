package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CurrencyControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/item/price/currency";

    @Test
    @DataSet(value = "datasets/e2e/item/currency/emptyE2ECurrencyDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/currency/testE2ECurrencyDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/item/currency/testE2ECurrencyDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/currency/updateE2ECurrencyDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update USD")
                .key("Update USD")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "USD")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/currency/testE2ECurrencyDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/currency/emptyE2ECurrencyDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "USD")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/currency/testE2ECurrencyDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<CurrencyDto> constantPayloadDtoList = List.of(
                CurrencyDto.builder()
                        .value("EUR")
                        .key("EUR")
                        .build(),
                CurrencyDto.builder()
                        .value("USD")
                        .key("USD")
                        .build());

        List<CurrencyDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<CurrencyDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/currency/testE2ECurrencyDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        CurrencyDto expected = CurrencyDto.builder()
                .value("USD")
                .key("USD")
                .build();

        CurrencyDto actual =
                webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(URL_TEMPLATE)
                                .queryParam("key", "USD")
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(CurrencyDto.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .value("USD")
                .key("USD")
                .build();
    }

}
