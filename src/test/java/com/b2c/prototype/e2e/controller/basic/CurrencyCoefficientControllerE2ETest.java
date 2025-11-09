package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CurrencyCoefficientControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/currency/coefficient";

    @Test
    @DataSet(value = "datasets/e2e/order/currency_coefficient/emptyCurrencyCoefficientDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/currency_coefficient/testCurrencyCoefficientDataSet.yml", orderBy = "id", ignoreCols = {"date_of_create"})
    @Sql(statements = "ALTER SEQUENCE currency_coefficient_id_seq RESTART WITH 3",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSaveCurrencyCoefficient() {
        CurrencyConvertDateDto dto = getCurrencyCoefficientDto();
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
    @DataSet(value = "datasets/e2e/order/currency_coefficient/emptyCurrencyCoefficientDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/currency_coefficient/updateCurrencyCoefficientDataSet.yml", orderBy = "id", ignoreCols = {"date_of_create"})
    void testUpdateCurrencyCoefficient() {
        CurrencyConvertDateDto dto = CurrencyConvertDateDto.builder()
                .currencyFrom(CurrencyDto.builder()
                        .key("EUR")
                        .value("EUR")
                        .build())
                .currencyTo(CurrencyDto.builder()
                        .key("EUR")
                        .value("EUR")
                        .build())
                .coefficient(1.05)
                .build();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("dateOfCreate", "2024-03-04")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/currency_coefficient/emptyCurrencyCoefficientDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/currency_coefficient/deleteCurrencyCoefficientDataSet.yml", orderBy = "id")
    void testDeleteCurrencyCoefficient() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("dateOfCreate", "2024-03-04")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/currency_coefficient/testCurrencyCoefficientDataSet.yml", cleanBefore = true)
    void testGetCurrencyCoefficient() {
        CurrencyConvertDateDto dto = CurrencyConvertDateDto.builder()
                .currencyFrom(CurrencyDto.builder()
                        .key("USD")
                        .value("USD")
                        .build())
                .currencyTo(CurrencyDto.builder()
                        .key("EUR")
                        .value("EUR")
                        .build())
                .coefficient(0.95)
                .dateOfCreate(LocalDate.parse("2024-03-03"))
                .build();

        CurrencyConvertDateDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("dateOfCreate", "2024-03-03")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(CurrencyConvertDateDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(dto);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/currency_coefficient/testCurrencyCoefficientDataSet.yml", cleanBefore = true)
    void testGetCurrencyCoefficients() {
        List<CurrencyConvertDateDto> constantPayloadDtoList = List.of(
                CurrencyConvertDateDto.builder()
                        .currencyFrom(CurrencyDto.builder()
                                .key("USD")
                                .value("USD")
                                .build())
                        .currencyTo(CurrencyDto.builder()
                                .key("EUR")
                                .value("EUR")
                                .build())
                        .coefficient(0.95)
                        .dateOfCreate(LocalDate.parse("2024-03-03"))
                        .build(),
                CurrencyConvertDateDto.builder()
                        .currencyFrom(CurrencyDto.builder()
                                .key("USD")
                                .value("USD")
                                .build())
                        .currencyTo(CurrencyDto.builder()
                                .key("USD")
                                .value("USD")
                                .build())
                        .coefficient(1)
                        .dateOfCreate(LocalDate.parse("2024-03-04"))
                        .build(),
                CurrencyConvertDateDto.builder()
                        .currencyFrom(CurrencyDto.builder()
                                .key("EUR")
                                .value("EUR")
                                .build())
                        .currencyTo(CurrencyDto.builder()
                                .key("USD")
                                .value("USD")
                                .build())
                        .coefficient(1.05)
                        .dateOfCreate(LocalDate.parse("2024-03-04"))
                        .build()
        );
        List<CurrencyConvertDateDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<CurrencyConvertDateDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("dateOfCreate")
                .isEqualTo(constantPayloadDtoList);
    }

    private CurrencyConvertDateDto getCurrencyCoefficientDto() {
        return CurrencyConvertDateDto.builder()
                .currencyFrom(CurrencyDto.builder()
                        .key("EUR")
                        .value("EUR")
                        .build())
                .currencyTo(CurrencyDto.builder()
                        .key("USD")
                        .value("USD")
                        .build())
                .coefficient(1.05)
                .build();
    }

}
