package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.time.ZoneId;
import java.util.List;

import static com.b2c.prototype.util.Constant.VALUE;
import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TimeDurationOptionControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/order/option/timeduration";

    @Test
    @DataSet(value = "datasets/e2e/order/time_duration_option/emptyE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/time_duration_option/testE2ETimeDurationOptionDataSet.yml", orderBy = "id")
    public void testSaveEntity() {
        TimeDurationOptionDto dto = getTimeDurationOptionDto();
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
    @DataSet(value = "datasets/e2e/order/time_duration_option/testE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/time_duration_option/updateE2ETimeDurationOptionDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        TimeDurationOptionDto constantPayloadDto = TimeDurationOptionDto.builder()
                .label("14-16")
                .value("USD_NY_14:00:00-16:00:00")
                .startTime(getLocalDateTime("1970-01-01 14:00:00"))
                .endTime(getLocalDateTime("1970-01-01 16:00:00"))
                .timeZone(ZoneId.of("UTC"))
                .price(PriceDto.builder()
                        .amount(15.0)
                        .currency("USD")
                        .build())
                .build();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(VALUE, "USD_NY_12:00:00-14:00:00")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/time_duration_option/testE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/time_duration_option/updateE2ETimeDurationOptionDataSet.yml", orderBy = "id")
    public void testPatchTEntity() {
        TimeDurationOptionDto constantPayloadDto = TimeDurationOptionDto.builder()
                .label("14-16")
                .value("USD_NY_14:00:00-16:00:00")
                .startTime(getLocalDateTime("1970-01-01 14:00:00"))
                .endTime(getLocalDateTime("1970-01-01 16:00:00"))
                .timeZone(ZoneId.of("UTC"))
                .price(PriceDto.builder()
                        .amount(15.0)
                        .currency("USD")
                        .build())
                .build();
        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(VALUE, "USD_NY_12:00:00-14:00:00")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/time_duration_option/testE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/order/time_duration_option/emptyE2ETimeDurationOptionDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(VALUE, "USD_NY_12:00:00-14:00:00")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/order/time_duration_option/testE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        TimeDurationOptionDto expected = getTimeDurationOptionDto();
        TimeDurationOptionDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam(VALUE, "USD_NY_12:00:00-14:00:00")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(TimeDurationOptionDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/order/time_duration_option/testE2ETimeDurationOptionDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<TimeDurationOptionDto > constantPayloadDtoList = List.of(
                TimeDurationOptionDto.builder()
                        .label("9-11")
                        .value("USD_NY_09:00:00-11:00:00")
                        .startTime(getLocalDateTime("1970-01-01 09:00:00"))
                        .endTime(getLocalDateTime("1970-01-01 11:00:00"))
                        .timeZone(ZoneId.of("UTC"))
                        .price(PriceDto.builder()
                                .amount(10.0)
                                .currency("USD")
                                .build())
                        .build(),
                TimeDurationOptionDto.builder()
                        .label("12-14")
                        .value("USD_NY_12:00:00-14:00:00")
                        .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                        .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                        .timeZone(ZoneId.of("UTC"))
                        .price(PriceDto.builder()
                                .amount(15.0)
                                .currency("USD")
                                .build())
                        .build());

        List<TimeDurationOptionDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<TimeDurationOptionDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(constantPayloadDtoList)
                .usingRecursiveComparison()
                .isEqualTo(actual);
    }

    private TimeDurationOptionDto getTimeDurationOptionDto() {
        return TimeDurationOptionDto.builder()
                .label("12-14")
                .value("USD_NY_12:00:00-14:00:00")
                .startTime(getLocalDateTime("1970-01-01 12:00:00"))
                .endTime(getLocalDateTime("1970-01-01 14:00:00"))
                .timeZone(ZoneId.of("UTC"))
                .price(PriceDto.builder()
                        .amount(15.0)
                        .currency("USD")
                        .build())
                .build();
    }

}
