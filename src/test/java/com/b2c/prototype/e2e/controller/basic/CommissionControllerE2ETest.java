package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.dto.payload.commission.CommissionValueDto;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommissionControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/commission";
    
    @Test
    @DataSet(value = "datasets/e2e/order/commission/emptyMinMaxCommissionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/commission/testMinMaxCommissionDataSet.yml",
            orderBy = {"id"},
            ignoreCols = {"key", "last_update_timestamp"})
    @Sql(statements = {
            "ALTER SEQUENCE min_max_commission_id_seq RESTART WITH 2",
            "ALTER SEQUENCE commission_value_id_seq RESTART WITH 3",
            "ALTER SEQUENCE price_id_seq RESTART WITH 2"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSaveEntity() {
        MinMaxCommissionDto dto = getMinMaxCommissionDto();
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
    @DataSet(value = "datasets/e2e/order/commission/testMinMaxCommissionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/commission/updateMinMaxCommissionDataSet.yml", orderBy = "id", ignoreCols = {"last_update_timestamp"})
    void testUpdateEntity() {
        MinMaxCommissionDto dto = getUpdateMinMaxCommissionDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("key", "key2")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/commission/testMinMaxCommissionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/commission/emptyMinMaxCommissionDataSet.yml", orderBy = "id")
    void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("key", "key2")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/commission/testMinMaxCommissionDataSet.yml", cleanBefore = true)
    void testGetEntity() {
        MinMaxCommissionDto dto = getMinMaxCommissionDto();
        dto.setKey("key2");

        MinMaxCommissionDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("key", "key2")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(MinMaxCommissionDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("lastUpdateTimestamp")
                .ignoringCollectionOrder()
                .isEqualTo(dto);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/commission/testMinMaxCommissionDataSet.yml", cleanBefore = true)
    void testGetEntities() {
        List<MinMaxCommissionDto> constantPayloadDtoList = List.of(
                MinMaxCommissionDto.builder()
                        .minCommissionValue(CommissionValueDto.builder()
                                .amount(100.0)
                                .feeType(FeeType.FIXED.name())
                                .currency(CurrencyDto.builder()
                                        .value("USD")
                                        .key("USD")
                                        .build())
                                .build())
                        .maxCommissionValue(CommissionValueDto.builder()
                                .amount(10.0)
                                .feeType(FeeType.PERCENTAGE.name())
                                .currency(null)
                                .build())
                        .changeCommissionPrice(PriceDto.builder()
                                .amount(200.0)
                                .currency(CurrencyDto.builder()
                                        .value("USD")
                                        .key("USD")
                                        .build())
                                .build())
                        .region("Global")
                        .key("key1")
                        .build(),
                MinMaxCommissionDto.builder()
                        .minCommissionValue(CommissionValueDto.builder()
                                .amount(10.0)
                                .feeType(FeeType.FIXED.name())
                                .currency(CurrencyDto.builder()
                                        .value("USD")
                                        .key("USD")
                                        .build())
                                .build())
                        .maxCommissionValue(CommissionValueDto.builder()
                                .amount(5.0)
                                .feeType(FeeType.PERCENTAGE.name())
                                .currency(null)
                                .build())
                        .changeCommissionPrice(PriceDto.builder()
                                .amount(8.0)
                                .currency(CurrencyDto.builder()
                                        .value("USD")
                                        .key("USD")
                                        .build())
                                .build())
                        .region("Global")
                        .key("key2")
                        .build()
        );
        List<MinMaxCommissionDto> actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE + "/all")
                        .queryParam("region", "Global")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<MinMaxCommissionDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("lastUpdateTimestamp")
                .isEqualTo(constantPayloadDtoList);
    }

    private MinMaxCommissionDto getMinMaxCommissionDto() {
        return MinMaxCommissionDto.builder()
                .minCommissionValue(CommissionValueDto.builder()
                        .amount(10.0)
                        .feeType(FeeType.FIXED.name())
                        .currency(CurrencyDto.builder()
                                .value("USD")
                                .key("USD")
                                .build())
                        .build())
                .maxCommissionValue(CommissionValueDto.builder()
                        .amount(5.0)
                        .feeType(FeeType.PERCENTAGE.name())
                        .currency(null)
                        .build())
                .changeCommissionPrice(PriceDto.builder()
                        .amount(8.0)
                        .currency(CurrencyDto.builder()
                                .value("USD")
                                .key("USD")
                                .build())
                        .build())
                .region("Global")
                .build();
    }

    private MinMaxCommissionDto getUpdateMinMaxCommissionDto() {
        return MinMaxCommissionDto.builder()
                .minCommissionValue(CommissionValueDto.builder()
                        .amount(20.0)
                        .feeType(FeeType.FIXED.name())
                        .currency(CurrencyDto.builder()
                                .value("USD")
                                .key("USD")
                                .build())
                        .build())
                .maxCommissionValue(CommissionValueDto.builder()
                        .amount(10.0)
                        .feeType(FeeType.PERCENTAGE.name())
                        .currency(null)
                        .build())
                .changeCommissionPrice(PriceDto.builder()
                        .amount(16.0)
                        .currency(CurrencyDto.builder()
                                .value("USD")
                                .key("USD")
                                .build())
                        .build())
                .region("DE")
                .key("key2")
                .build();
    }
}
