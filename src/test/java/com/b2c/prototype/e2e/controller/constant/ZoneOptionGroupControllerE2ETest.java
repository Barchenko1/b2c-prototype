package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.ZoneOptionDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ZoneOptionGroupControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/order/option/zone";

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/emptyE2EZoneOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionDataSet.yml", orderBy = "id", ignoreCols = {"price_id"})
    public void testSaveEntity() {
        ZoneOptionGroupDto dto = getZoneOptionGroupDto();
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
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/zone_option/updateE2EZoneOptionDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ZoneOptionDto constantPayloadDto = ZoneOptionDto.builder()
                .label("Update ZoneA")
                .value("Update ZoneA")
                .city("New York")
                .price(PriceDto.builder()
                        .amount(30.0)
//                        .currency("USD")
                        .build())
                .build();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "ZoneA")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/zone_option/updateE2EZoneOptionDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ZoneOptionDto constantPayloadDto = ZoneOptionDto.builder()
                .label("Update ZoneA")
                .value("Update ZoneA")
                .city("New York")
                .price(PriceDto.builder()
                        .amount(30.0)
//                        .currency("USD")
                        .build())
                .build();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "ZoneA")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/zone_option/emptyE2EZoneOptionDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "ZoneB")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        ZoneOptionGroupDto expected = getZoneOptionGroupDto();
        ZoneOptionDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "ZoneB")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(ZoneOptionDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<ZoneOptionDto> constantPayloadDtoList = List.of(
                ZoneOptionDto.builder()
                        .label("ZoneA")
                        .value("ZoneA")
                        .city("New York")
                        .price(PriceDto.builder()
                                .amount(27.0)
//                                .currency("USD")
                                .build())
                        .build(),
                ZoneOptionDto.builder()
                        .label("ZoneB")
                        .value("ZoneB")
                        .city("New York")
                        .price(PriceDto.builder()
                                .amount(25.0)
//                                .currency("USD")
                                .build())
                        .build());

        List<ZoneOptionDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<ZoneOptionDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    private ZoneOptionGroupDto getZoneOptionGroupDto() {
        return ZoneOptionGroupDto.builder()
                .label("")
                .value("")
                .zoneOptions(List.of(
                        ZoneOptionDto.builder()
                                .label("ZoneA")
                                .value("ZoneB")
                                .city("New York")
                                .price(PriceDto.builder()
                                        .amount(25.0)
                                        .currency(CurrencyDto.builder()
                                                .label("USD")
                                                .value("USD")
                                                .build())
                                        .build())
                                .build(),
                        ZoneOptionDto.builder()
                                .label("ZoneB")
                                .value("ZoneB")
                                .city("New York")
                                .price(PriceDto.builder()
                                        .amount(25.0)
                                        .currency(CurrencyDto.builder()
                                                .label("USD")
                                                .value("USD")
                                                .build())
                                        .build())
                                .build()
                ))
                .build();
    }
}
