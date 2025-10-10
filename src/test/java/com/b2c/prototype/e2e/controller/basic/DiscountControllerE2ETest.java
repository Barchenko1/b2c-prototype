package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DiscountControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/discount";

    @Test
    @DataSet(value = "datasets/e2e/item/discount/emptyE2EDiscountDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/discount/testE2EDiscountDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        DiscountDto dto = getDiscountDto();
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
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/discount/updateE2EDiscountDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        DiscountDto discountDto = DiscountDto.builder()
//                .label("Apple")
//                .value("Update Apple")
                .build();

        String jsonPayload = writeValueAsString(discountDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Apple")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/discount/updateE2EDiscountDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        DiscountDto discountDto = DiscountDto.builder()
//                .label("Apple")
//                .value("Update Apple")
                .build();

        String jsonPayload = writeValueAsString(discountDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Apple")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/discount/emptyE2EDiscountDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Apple")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<DiscountDto> constantPayloadDtoList = List.of(
                DiscountDto.builder()
//                        .label("Android")
//                        .value("Android")
                        .build(),
                DiscountDto.builder()
//                        .label("Apple")
//                        .value("Apple")
                        .build());

        List<DiscountDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<DiscountDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        DiscountDto expected = DiscountDto.builder()
//                .label("Apple")
//                .value("Apple")
                .build();

        DiscountDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Apple")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(DiscountDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    private DiscountDto getDiscountDto() {
        return DiscountDto.builder()
                .charSequenceCode("abc")
                .amount(10)
                .currency("USD")
                .isActive(true)
                .articularIdSet(Set.of("123"))
                .build();
    }
}
