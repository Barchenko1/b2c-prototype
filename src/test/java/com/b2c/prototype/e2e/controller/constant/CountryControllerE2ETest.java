package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CountryControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/order/delivery/country";

    @Test
    @DataSet(value = "datasets/e2e/order/country/emptyE2ECountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        CountryDto dto = getCountryDto();
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
    @DataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/country/updateE2ECountryDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        CountryDto countryDto = CountryDto.builder()
                .value("Update USA")
                .key("Update USA")
                .flagImagePath("/images/usa.jpg")
                .build();
        String jsonPayload = writeValueAsString(countryDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "USA")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/country/emptyE2ECountryDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "USA")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<CountryDto> constantPayloadDtoList = List.of(
                CountryDto.builder()
                        .value("CANADA")
                        .key("CANADA")
                        .flagImagePath("/images/canada.jpg")
                        .build(),
                CountryDto.builder()
                        .value("USA")
                        .key("USA")
                        .flagImagePath("/images/usa.jpg")
                        .build());

        List<CountryDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<CountryDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/country/testE2ECountryDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        CountryDto expected = CountryDto.builder()
                .value("USA")
                .key("USA")
                .flagImagePath("/images/usa.jpg")
                .build();
        CountryDto actual = webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(URL_TEMPLATE)
                                .queryParam("key", "USA")
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(CountryDto.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private CountryDto getCountryDto() {
        return CountryDto.builder()
                .value("USA")
                .key("USA")
                .flagImagePath("/images/usa.jpg")
                .build();
    }

}
