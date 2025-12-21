package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.tenant.TenantDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TenantControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/tenant";

    @Test
    @DataSet(value = "datasets/e2e/tenant/emptyE2ETenantDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", orderBy = "id")
    @Sql(statements = {
            "ALTER SEQUENCE currency_id_seq RESTART WITH 2",
            "ALTER SEQUENCE tenant_id_seq RESTART WITH 2"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCreateEntity() {
        TenantDto dto = getTenantDto();
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
    @DataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/tenant/updateE2ETenantDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        TenantDto constantPayloadDto = TenantDto.builder()
                .code("Update Global")
                .value("Update Global")
                .language("Update English")
                .defaultLocale("Update en-US")
                .primaryCurrency(CurrencyDto.builder()
                        .key("EUR")
                        .value("EUR")
                        .build())
                .timezone("America/New_York")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("code", "Global")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/tenant/emptyE2ETenantDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("code", "Global")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<TenantDto> constantPayloadDtoList = List.of(
                TenantDto.builder()
                        .code("DE")
                        .value("Germany")
                        .language("German")
                        .defaultLocale("de-DE")
                        .primaryCurrency(CurrencyDto.builder()
                                .key("EUR")
                                .value("EUR")
                                .build())
                        .timezone("Europe/Berlin")
                        .build(),
                TenantDto.builder()
                        .code("Global")
                        .value("Global")
                        .language("English")
                        .defaultLocale("en-US")
                        .primaryCurrency(CurrencyDto.builder()
                                .key("USD")
                                .value("USD")
                                .build())
                        .timezone("UTC")
                        .build());

        List<TenantDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<TenantDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        TenantDto expected = TenantDto.builder()
                .code("Global")
                .value("Global")
                .language("English")
                .defaultLocale("en-US")
                .primaryCurrency(CurrencyDto.builder()
                        .key("USD")
                        .value("USD")
                        .build())
                .timezone("UTC")
                .build();;

        TenantDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("code", "Global")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(TenantDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private TenantDto getTenantDto() {
        return TenantDto.builder()
                .code("Global")
                .value("Global")
                .language("English")
                .defaultLocale("en-US")
                .primaryCurrency(CurrencyDto.builder()
                        .key("USD")
                        .value("USD")
                        .build())
                .timezone("UTC")
                .build();
    }

}
