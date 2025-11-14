package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryPhoneCodeDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CountryPhoneCodeControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/user/phone/country-code";

    @Test
    @DataSet(value = "datasets/e2e/user/country_phone_code/emptyE2ECountryPhoneCodeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/country_phone_code/testE2ECountryPhoneCodeDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/user/country_phone_code/testE2ECountryPhoneCodeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/country_phone_code/updateE2ECountryPhoneCodeDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update +11")
                .key("Update +11")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", UriUtils.encode("+11", StandardCharsets.UTF_8))
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/country_phone_code/testE2ECountryPhoneCodeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/country_phone_code/emptyE2ECountryPhoneCodeDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", UriUtils.encode("+22", StandardCharsets.UTF_8))
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/country_phone_code/testE2ECountryPhoneCodeDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<CountryPhoneCodeDto> constantPayloadDtoList = List.of(
                CountryPhoneCodeDto.builder()
                        .value("+11")
                        .key("+11")
                        .build(),
                CountryPhoneCodeDto.builder()
                        .value("+22")
                        .key("+22")
                        .build());

        List<CountryPhoneCodeDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<CountryPhoneCodeDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/user/country_phone_code/testE2ECountryPhoneCodeDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        CountryPhoneCodeDto currencyDto = CountryPhoneCodeDto.builder()
                .value("+22")
                .key("+22")
                .build();

        CountryPhoneCodeDto actual =
                webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(URL_TEMPLATE)
                                .queryParam("key", UriUtils.encode("+22", StandardCharsets.UTF_8))
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(CountryPhoneCodeDto.class)
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(currencyDto);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .value("+22")
                .key("+22")
                .build();
    }

}
