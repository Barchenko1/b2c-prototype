package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DiscountGroupControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/group/discount";

    @Test
    @DataSet(value = "datasets/e2e/item/discount/emptyE2EDiscountGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/discount/testE2EDiscountGroupDataSet.yml", orderBy = "id")
    @Sql(statements = {
            "ALTER SEQUENCE discount_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE discount_id_seq RESTART WITH 2",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCreateEntity() {
        DiscountGroupDto dto = getDiscountGroupDto();
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
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/discount/updateE2EDiscountGroupDataSetMore.yml", orderBy = "id")
    @Sql(statements = {
            "ALTER SEQUENCE discount_id_seq RESTART WITH 4"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntityMore() {
        DiscountGroupDto discountGroupDto = getUpdateDiscountGroupDtoMore();
        String jsonPayload = writeValueAsString(discountGroupDto);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("key", "Global_group")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/discount/updateE2EDiscountGroupDataSetLess.yml", orderBy = "id")
    public void testUpdateEntityLess() {
        DiscountGroupDto discountGroupDto = getUpdateDiscountGroupDtoLess();
        String jsonPayload = writeValueAsString(discountGroupDto);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("key", "Global_group")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/discount/updateE2EDiscountChangeStatusDataSet.yml", orderBy = "id")
    public void testUpdateEntityStatus() {
        DiscountStatusDto discountGroupDto = getDiscountStatusDto();
        String jsonPayload = writeValueAsString(discountGroupDto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/discount/emptyE2EDiscountGroupDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("key", "Global_group")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountGroupDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<DiscountGroupDto> expected = List.of(
                DiscountGroupDto.builder()
                        .key("Global_group")
                        .value("Global_group")
//                        .regionCode("Global")
                        .discounts(List.of(
                                DiscountDto.builder()
                                        .charSequenceCode("abc2")
                                        .amount(20)
                                        .currency(CurrencyDto.builder()
                                                .key("USD")
                                                .value("USD")
                                                .build())
                                        .isActive(false)
                                        .articularIdSet(Set.of())
                                        .build(),
                                DiscountDto.builder()
                                        .charSequenceCode("abc3")
                                        .amount(10)
                                        .currency(null)
                                        .isActive(false)
                                        .isPercent(true)
                                        .articularIdSet(Set.of())
                                        .build()))
                        .build());

        List<DiscountGroupDto> actual =
                webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(URL_TEMPLATE + "/all")
                                .queryParam("region", "Global")
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<DiscountGroupDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/discount/testE2EDiscountGroupDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        DiscountGroupDto expected = DiscountGroupDto.builder()
                .key("Global_group")
                .value("Global_group")
//                .regionCode("Global")
                .discounts(List.of(
                        DiscountDto.builder()
                                .charSequenceCode("abc2")
                                .amount(20)
                                .currency(CurrencyDto.builder()
                                        .key("USD")
                                        .value("USD")
                                        .build())
                                .isActive(false)
                                .articularIdSet(Set.of())
                                .build(),
                        DiscountDto.builder()
                                .charSequenceCode("abc3")
                                .amount(10)
                                .currency(null)
                                .isActive(false)
                                .isPercent(true)
                                .articularIdSet(Set.of())
                                .build()))
                .build();

        DiscountGroupDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("key", "Global_group")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(DiscountGroupDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private DiscountGroupDto getDiscountGroupDto() {
        return DiscountGroupDto.builder()
                .key("Global_group")
                .value("Global_group")
//                .regionCode("Global")
                .discounts(List.of(
                        DiscountDto.builder()
                                .charSequenceCode("abc2")
                                .amount(20)
                                .currency(CurrencyDto.builder()
                                        .key("USD")
                                        .value("USD")
                                        .build())
                                .isActive(false)
                                .articularIdSet(Set.of("123"))
                                .build(),
                        DiscountDto.builder()
                                .charSequenceCode("abc3")
                                .amount(10)
                                .currency(null)
                                .isActive(false)
                                .isPercent(true)
                                .articularIdSet(Set.of("123"))
                                .build()))
                .build();
    }

    private DiscountGroupDto getUpdateDiscountGroupDtoMore() {
        return DiscountGroupDto.builder()
                .key("Update Global_group")
                .value("Update Global_group")
//                .regionCode("DE")
                .discounts(List.of(
                        DiscountDto.builder()
                                .searchKey("abc2")
                                .charSequenceCode("Update abc2")
                                .amount(2)
                                .currency(null)
                                .isActive(true)
                                .isPercent(true)
                                .articularIdSet(Set.of("123"))
                                .build(),
                        DiscountDto.builder()
                                .searchKey("abc3")
                                .charSequenceCode("Update abc3")
                                .amount(8)
                                .currency(CurrencyDto.builder()
                                        .key("EUR")
                                        .value("EUR")
                                        .build())
                                .isActive(true)
                                .isPercent(false)
                                .articularIdSet(Set.of("123"))
                                .build(),
                        DiscountDto.builder()
                                .searchKey(null)
                                .charSequenceCode("New abc4")
                                .amount(5)
                                .currency(CurrencyDto.builder()
                                        .key("EUR")
                                        .value("EUR")
                                        .build())
                                .isActive(true)
                                .isPercent(false)
                                .articularIdSet(Set.of("123"))
                                .build()))
                .build();
    }

    private DiscountGroupDto getUpdateDiscountGroupDtoLess() {
        return DiscountGroupDto.builder()
                .key("Update Global_group")
                .value("Update Global_group")
//                .regionCode("DE")
                .discounts(List.of(
                        DiscountDto.builder()
                                .searchKey("abc2")
                                .charSequenceCode("Update abc2")
                                .amount(2)
                                .currency(null)
                                .isActive(true)
                                .isPercent(true)
                                .articularIdSet(Set.of("123"))
                                .build()))
                .build();
    }

    private DiscountStatusDto getDiscountStatusDto() {
        return DiscountStatusDto.builder()
                .region("Global")
                .groupCode("Global_group")
                .charSequenceCode("abc3")
                .isActive(true)
                .build();
    }
}
