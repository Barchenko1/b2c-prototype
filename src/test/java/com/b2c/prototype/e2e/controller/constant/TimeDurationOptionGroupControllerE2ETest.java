package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.TimeDurationOptionDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZoneId;
import java.util.List;

import static com.b2c.prototype.util.Constant.VALUE;
import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TimeDurationOptionGroupControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/option/group/duration";

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/time_duration_option/emptyE2ETimeDurationOptionDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE time_duration_option RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE option_group RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE price RESTART IDENTITY CASCADE",
            })
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/time_duration_option/testE2ETimeDurationOptionDataSet.yml", orderBy = "id")
    public void testSaveEntity() {
        TimeDurationOptionGroupDto dto = getTimeDurationOptionGroupDto();
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
    @DataSet(value = "datasets/e2e/order/option_group/time_duration_option/testUpdateE2ETimeDurationOptionGroupDataSet.yml", cleanBefore = true, disableConstraints = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/time_duration_option/updateE2ETimeDurationOptionDataSet.yml", orderBy = "id", ignoreCols = {"id"})
    @Sql(statements = "ALTER SEQUENCE price_id_seq RESTART WITH 5",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntity() {
        TimeDurationOptionGroupDto constantPayloadDto = TimeDurationOptionGroupDto.builder()
                .label("NY")
                .value("NY")
                .timeDurationOptions(List.of(
                        TimeDurationOptionDto.builder()
                                .searchValue("12-14")
                                .label("Update 12-14")
                                .value("Update 12-14")
                                .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                                .timeZone(ZoneId.of("UTC"))
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .label("USD")
                                                .value("USD")
                                                .build())
                                        .build())
                                .build(),
                        TimeDurationOptionDto.builder()
                                .searchValue(null)
                                .label("16-18")
                                .value("16-18")
                                .startTime(getLocalDateTime("1970-01-01 16:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 18:00:00"))
                                .timeZone(ZoneId.of("UTC"))
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
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(VALUE, "NY")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/time_duration_option/testUpdateE2ETimeDurationOptionGroupDataSet.yml", cleanBefore = true, disableConstraints = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/time_duration_option/updateE2ETimeDurationOptionDataSet.yml", orderBy = "id", ignoreCols = {"id"})
    @Sql(statements = "ALTER SEQUENCE price_id_seq RESTART WITH 5",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testPatchEntity() {
        TimeDurationOptionGroupDto constantPayloadDto = TimeDurationOptionGroupDto.builder()
                .label("NY")
                .value("NY")
                .timeDurationOptions(List.of(
                        TimeDurationOptionDto.builder()
                                .searchValue("12-14")
                                .label("Update 12-14")
                                .value("Update 12-14")
                                .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                                .timeZone(ZoneId.of("UTC"))
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .label("USD")
                                                .value("USD")
                                                .build())
                                        .build())
                                .build(),
                        TimeDurationOptionDto.builder()
                                .searchValue(null)
                                .label("16-18")
                                .value("16-18")
                                .startTime(getLocalDateTime("1970-01-01 16:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 18:00:00"))
                                .timeZone(ZoneId.of("UTC"))
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
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(VALUE, "NY")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/time_duration_option/testE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/time_duration_option/emptyE2ETimeDurationOptionDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(VALUE, "NY")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/time_duration_option/testE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        TimeDurationOptionGroupDto expected = getTimeDurationOptionGroupDto();
        expected.getTimeDurationOptions().forEach(timeDurationOptionDto -> {
            timeDurationOptionDto.setSearchValue(timeDurationOptionDto.getValue());
        });
        TimeDurationOptionGroupDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(VALUE, "NY")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(TimeDurationOptionGroupDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/time_duration_option/testE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<TimeDurationOptionGroupDto > expected = List.of(
                TimeDurationOptionGroupDto.builder()
                        .label("CA")
                        .value("CA")
                        .timeDurationOptions(List.of(
                                TimeDurationOptionDto.builder()
                                        .label("9-11")
                                        .value("9-11")
                                        .startTime(getLocalDateTime("1970-01-01 09:00:00"))
                                        .endTime(getLocalDateTime("1970-01-01 11:00:00"))
                                        .timeZone(ZoneId.of("UTC"))
                                        .price(PriceDto.builder()
                                                .amount(20.0)
                                                .currency(CurrencyDto.builder()
                                                        .label("USD")
                                                        .value("USD")
                                                        .build())
                                                .build())
                                        .build(),
                                TimeDurationOptionDto.builder()
                                        .label("12-14")
                                        .value("12-14")
                                        .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                                        .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                                        .timeZone(ZoneId.of("UTC"))
                                        .price(PriceDto.builder()
                                                .amount(10.0)
                                                .currency(CurrencyDto.builder()
                                                        .label("USD")
                                                        .value("USD")
                                                        .build())
                                                .build())
                                        .build()))
                        .build(),
                TimeDurationOptionGroupDto.builder()
                        .label("NY")
                        .value("NY")
                        .timeDurationOptions(List.of(
                                TimeDurationOptionDto.builder()
                                    .label("9-11")
                                    .value("9-11")
                                    .startTime(getLocalDateTime("1970-01-01 09:00:00"))
                                    .endTime(getLocalDateTime("1970-01-01 11:00:00"))
                                    .timeZone(ZoneId.of("UTC"))
                                    .price(PriceDto.builder()
                                            .amount(20.0)
                                            .currency(CurrencyDto.builder()
                                                    .label("USD")
                                                    .value("USD")
                                                    .build())
                                            .build())
                                    .build(),
                                TimeDurationOptionDto.builder()
                                        .label("12-14")
                                        .value("12-14")
                                        .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                                        .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                                        .timeZone(ZoneId.of("UTC"))
                                        .price(PriceDto.builder()
                                                .amount(10.0)
                                                .currency(CurrencyDto.builder()
                                                        .label("USD")
                                                        .value("USD")
                                                        .build())
                                                .build())
                                        .build()))
                        .build()
        );
        expected.forEach(optionGroup ->
                optionGroup.getTimeDurationOptions().forEach(
                timeDurationOptionDto ->
                        timeDurationOptionDto.setSearchValue(timeDurationOptionDto.getValue())));

        List<TimeDurationOptionGroupDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<TimeDurationOptionGroupDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private TimeDurationOptionGroupDto getTimeDurationOptionGroupDto() {
        return TimeDurationOptionGroupDto.builder()
                .label("NY")
                .value("NY")
                .timeDurationOptions(List.of(
                        TimeDurationOptionDto.builder()
                                .label("9-11")
                                .value("9-11")
                                .startTime(getLocalDateTime("1970-01-01 09:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 11:00:00"))
                                .timeZone(ZoneId.of("UTC"))
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .label("USD")
                                                .value("USD")
                                                .build())
                                        .build())
                                .build(),
                        TimeDurationOptionDto.builder()
                                .label("12-14")
                                .value("12-14")
                                .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                                .timeZone(ZoneId.of("UTC"))
                                .price(PriceDto.builder()
                                        .amount(10.0)
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
