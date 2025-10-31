package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
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

    private static final String URL_TEMPLATE = "/api/v1/option/group/zone";

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/emptyE2EZoneOptionGroupDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE zone_option_group RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE zone_option RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE price RESTART IDENTITY CASCADE",
            })
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionGroupDataSet.yml", orderBy = {"id"})
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
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/zone_option/updateE2EZoneOptionGroupDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ZoneOptionGroupDto constantPayloadDto = getUpdateZoneOptionGroupDto();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "NY-Zone")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/zone_option/updateE2EZoneOptionGroupDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ZoneOptionGroupDto constantPayloadDto = getUpdateZoneOptionGroupDto();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "NY-Zone")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/zone_option/emptyE2EZoneOptionGroupDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "NY-Zone")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionGroupDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        ZoneOptionGroupDto expected = ZoneOptionGroupDto.builder()
                .label("NY-Zone")
                .value("NY-Zone")
                .city("New York")
                .country(CountryDto.builder()
                        .label("USA")
                        .value("USA")
                        .build())
                .zoneOptions(List.of(
                        ZoneOptionDto.builder()
                                .searchValue("NY-ZoneA")
                                .label("NY-ZoneA")
                                .value("NY-ZoneA")
                                .price(PriceDto.builder()
                                        .amount(25.0)
                                        .currency(CurrencyDto.builder()
                                                .label("USD")
                                                .value("USD")
                                                .build())
                                        .build())
                                .build(),
                        ZoneOptionDto.builder()
                                .searchValue("NY-ZoneB")
                                .label("NY-ZoneB")
                                .value("NY-ZoneB")
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
        ZoneOptionGroupDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "NY-Zone")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(ZoneOptionGroupDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/zone_option/testE2EZoneOptionGroupDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<ZoneOptionGroupDto> constantPayloadDtoList = List.of(
                ZoneOptionGroupDto.builder()
                        .label("CA-Zone")
                        .value("CA-Zone")
                        .city("California")
                        .country(CountryDto.builder()
                                .label("USA")
                                .value("USA")
                                .build())
                        .zoneOptions(List.of(
                                ZoneOptionDto.builder()
                                        .searchValue("CA-ZoneA")
                                        .label("CA-ZoneA")
                                        .value("CA-ZoneA")
                                        .price(PriceDto.builder()
                                                .amount(10.0)
                                                .currency(CurrencyDto.builder()
                                                        .label("USD")
                                                        .value("USD")
                                                        .build())
                                                .build())
                                        .build(),
                                ZoneOptionDto.builder()
                                        .searchValue("CA-ZoneB")
                                        .label("CA-ZoneB")
                                        .value("CA-ZoneB")
                                        .price(PriceDto.builder()
                                                .amount(10.0)
                                                .currency(CurrencyDto.builder()
                                                        .label("USD")
                                                        .value("USD")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build(),
                ZoneOptionGroupDto.builder()
                        .label("NY-Zone")
                        .value("NY-Zone")
                        .city("New York")
                        .country(CountryDto.builder()
                                .label("USA")
                                .value("USA")
                                .build())
                        .zoneOptions(List.of(
                                ZoneOptionDto.builder()
                                        .searchValue("NY-ZoneA")
                                        .label("NY-ZoneA")
                                        .value("NY-ZoneA")
                                        .price(PriceDto.builder()
                                                .amount(25.0)
                                                .currency(CurrencyDto.builder()
                                                        .label("USD")
                                                        .value("USD")
                                                        .build())
                                                .build())
                                        .build(),
                                ZoneOptionDto.builder()
                                        .searchValue("NY-ZoneB")
                                        .label("NY-ZoneB")
                                        .value("NY-ZoneB")
                                        .price(PriceDto.builder()
                                                .amount(25.0)
                                                .currency(CurrencyDto.builder()
                                                        .label("USD")
                                                        .value("USD")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build());

        List<ZoneOptionGroupDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<ZoneOptionGroupDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(constantPayloadDtoList);
    }

    private ZoneOptionGroupDto getZoneOptionGroupDto() {
        return ZoneOptionGroupDto.builder()
                .label("NY-Zone")
                .value("NY-Zone")
                .city("New York")
                .country(CountryDto.builder()
                        .label("USA")
                        .value("USA")
                        .build())
                .zoneOptions(List.of(
                        ZoneOptionDto.builder()
                                .searchValue("NY-ZoneA")
                                .label("NY-ZoneA")
                                .value("NY-ZoneA")
                                .price(PriceDto.builder()
                                        .amount(25.0)
                                        .currency(CurrencyDto.builder()
                                                .label("USD")
                                                .value("USD")
                                                .build())
                                        .build())
                                .build(),
                        ZoneOptionDto.builder()
                                .searchValue("NY-ZoneB")
                                .label("NY-ZoneB")
                                .value("NY-ZoneB")
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

    private ZoneOptionGroupDto getUpdateZoneOptionGroupDto() {
        return  ZoneOptionGroupDto.builder()
                .label("NY-Zone2")
                .value("NY-Zone2")
                .city("New York2")
                .country(CountryDto.builder()
                        .label("USA")
                        .value("USA")
                        .build())
                .zoneOptions(List.of(
                        ZoneOptionDto.builder()
                                .searchValue("NY-ZoneB")
                                .label("NY-ZoneB2")
                                .value("NY-ZoneB2")
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .label("USD")
                                                .value("USD")
                                                .build())
                                        .build())
                                .build(),
                        ZoneOptionDto.builder()
                                .searchValue("NY-ZoneA")
                                .label("NY-ZoneC")
                                .value("NY-ZoneC")
                                .price(PriceDto.builder()
                                        .amount(30.0)
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
