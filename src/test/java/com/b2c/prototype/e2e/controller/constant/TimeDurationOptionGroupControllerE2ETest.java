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

import static com.b2c.prototype.util.Constant.KEY;
import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TimeDurationOptionGroupControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/option/group/duration";

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/time_duration_option/emptyE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/time_duration_option/testE2ETimeDurationOptionDataSet.yml", orderBy = "id")
    @Sql(statements = {
            "ALTER SEQUENCE time_duration_option_id_seq RESTART WITH 3",
            "ALTER SEQUENCE option_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE price_id_seq RESTART WITH 3",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/time_duration_option/updateE2ETimeDurationOptionDataSetMore.yml", orderBy = "id", ignoreCols = {"id"})
    @Sql(statements = "ALTER SEQUENCE price_id_seq RESTART WITH 5",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntityMore() {
        TimeDurationOptionGroupDto constantPayloadDto = getUpdateTimeDurationOptionDtoMore();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(KEY, "NY")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/option_group/time_duration_option/testUpdateE2ETimeDurationOptionGroupDataSet.yml", cleanBefore = true, disableConstraints = true)
    @ExpectedDataSet(value = "datasets/e2e/order/option_group/time_duration_option/updateE2ETimeDurationOptionDataSetLess.yml", orderBy = "id", ignoreCols = {"id"})
    public void testUpdateEntityLess() {
        TimeDurationOptionGroupDto constantPayloadDto = getUpdateTimeDurationOptionDtoLess();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(KEY, "NY")
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
                        .queryParam(KEY, "NY")
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
            timeDurationOptionDto.setSearchKey(timeDurationOptionDto.getKey());
        });
        TimeDurationOptionGroupDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(KEY, "NY")
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
                        .value("CA")
                        .key("CA")
                        .timeDurationOptions(List.of(
                                TimeDurationOptionDto.builder()
                                        .value("9-11")
                                        .key("CA_9-11")
                                        .startTime(getLocalDateTime("1970-01-01 09:00:00"))
                                        .endTime(getLocalDateTime("1970-01-01 11:00:00"))
                                        .timeZone(ZoneId.of("UTC"))
                                        .price(PriceDto.builder()
                                                .amount(20.0)
                                                .currency(CurrencyDto.builder()
                                                        .value("USD")
                                                        .key("USD")
                                                        .build())
                                                .build())
                                        .build(),
                                TimeDurationOptionDto.builder()
                                        .value("12-14")
                                        .key("CA_12-14")
                                        .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                                        .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                                        .timeZone(ZoneId.of("UTC"))
                                        .price(PriceDto.builder()
                                                .amount(10.0)
                                                .currency(CurrencyDto.builder()
                                                        .value("USD")
                                                        .key("USD")
                                                        .build())
                                                .build())
                                        .build()))
                        .build(),
                TimeDurationOptionGroupDto.builder()
                        .value("NY")
                        .key("NY")
                        .timeDurationOptions(List.of(
                                TimeDurationOptionDto.builder()
                                    .value("9-11")
                                    .key("NY_9-11")
                                    .startTime(getLocalDateTime("1970-01-01 09:00:00"))
                                    .endTime(getLocalDateTime("1970-01-01 11:00:00"))
                                    .timeZone(ZoneId.of("UTC"))
                                    .price(PriceDto.builder()
                                            .amount(20.0)
                                            .currency(CurrencyDto.builder()
                                                    .value("USD")
                                                    .key("USD")
                                                    .build())
                                            .build())
                                    .build(),
                                TimeDurationOptionDto.builder()
                                        .value("12-14")
                                        .key("NY_12-14")
                                        .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                                        .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                                        .timeZone(ZoneId.of("UTC"))
                                        .price(PriceDto.builder()
                                                .amount(10.0)
                                                .currency(CurrencyDto.builder()
                                                        .value("USD")
                                                        .key("USD")
                                                        .build())
                                                .build())
                                        .build()))
                        .build()
        );
        expected.forEach(optionGroup ->
                optionGroup.getTimeDurationOptions().forEach(
                timeDurationOptionDto ->
                        timeDurationOptionDto.setSearchKey(timeDurationOptionDto.getKey())));

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
                .value("NY")
                .key("NY")
                .timeDurationOptions(List.of(
                        TimeDurationOptionDto.builder()
                                .value("9-11")
                                .key("NY_9-11")
                                .startTime(getLocalDateTime("1970-01-01 09:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 11:00:00"))
                                .timeZone(ZoneId.of("UTC"))
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build(),
                        TimeDurationOptionDto.builder()
                                .value("12-14")
                                .key("NY_12-14")
                                .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                                .timeZone(ZoneId.of("UTC"))
                                .price(PriceDto.builder()
                                        .amount(10.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build()
                ))
                .build();
    }

    private TimeDurationOptionGroupDto getUpdateTimeDurationOptionDtoMore() {
        return TimeDurationOptionGroupDto.builder()
                .value("NY")
                .key("NY")
                .timeDurationOptions(List.of(
                        TimeDurationOptionDto.builder()
                                .searchKey("NY_12-14")
                                .value("Update 12-14")
                                .key("Update 12-14")
                                .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                                .timeZone(ZoneId.of("UTC"))
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build(),
                        TimeDurationOptionDto.builder()
                                .searchKey(null)
                                .value("16-18")
                                .key("NY_16-18")
                                .startTime(getLocalDateTime("1970-01-01 16:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 18:00:00"))
                                .timeZone(ZoneId.of("UTC"))
                                .price(PriceDto.builder()
                                        .amount(30.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build(),
                        TimeDurationOptionDto.builder()
                                .searchKey(null)
                                .value("18-20")
                                .key("NY_18-20")
                                .startTime(getLocalDateTime("1970-01-01 18:00:00"))
                                .endTime(getLocalDateTime("1970-01-01 20:00:00"))
                                .timeZone(ZoneId.of("UTC"))
                                .price(PriceDto.builder()
                                        .amount(30.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build()
                ))
                .build();
    }

    private TimeDurationOptionGroupDto getUpdateTimeDurationOptionDtoLess() {
        return TimeDurationOptionGroupDto.builder()
                .value("NY")
                .key("NY")
                .timeDurationOptions(List.of())
                .build();
    }

}
