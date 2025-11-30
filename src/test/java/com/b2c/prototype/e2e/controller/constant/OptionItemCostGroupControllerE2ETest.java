package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OptionItemCostGroupControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/option/group/item/cost";

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item_cost/emptyE2EOptionGroupItemCostDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item_cost/testE2EOptionItemCostGroupDataSet.yml", orderBy = {"id"},
    ignoreCols = {"key"})
    @Sql(statements = {
            "ALTER SEQUENCE option_item_cost_id_seq RESTART WITH 3",
            "ALTER SEQUENCE option_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE price_id_seq RESTART WITH 3"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCreateEntity() {
        OptionItemCostGroupDto dto = getOptionGroupDto();
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
    @DataSet(value = "datasets/e2e/item/option_group/option_item_cost/testE2EOptionItemCostGroupDataSet.yml", cleanBefore = true, disableConstraints = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item_cost/updateE2EOptionItemCostGroupDataSetMore.yml", orderBy = "id", ignoreCols = {"id","price_id"})
    @Sql(statements = {
            "ALTER SEQUENCE option_item_cost_id_seq RESTART WITH 5",
            "ALTER SEQUENCE price_id_seq RESTART WITH 5"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntityMore() {
        OptionItemCostGroupDto constantPayloadDto = OptionItemCostGroupDto.builder()
                .value("Update Color")
                .key("Update Color")
                .optionItemCosts(List.of(
                        OptionItemCostDto.builder()
//                                .searchKey("Red")
                                .value("Update Red")
                                .key("Red")
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build(),
                        OptionItemCostDto.builder()
                                .value("Yellow")
                                .key("Yellow")
                                .price(PriceDto.builder()
                                        .amount(30.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build(),
                        OptionItemCostDto.builder()
                                .value("White")
                                .key("White")
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

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Color")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item_cost/testE2EOptionItemCostGroupDataSet.yml", cleanBefore = true, disableConstraints = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item_cost/updateE2EOptionItemCostGroupDataSetLess.yml", orderBy = "id", ignoreCols = {"id","price_id"})
    public void testUpdateEntityLess() {
        OptionItemCostGroupDto constantPayloadDto = OptionItemCostGroupDto.builder()
                .value("Update Color")
                .key("Update Color")
                .optionItemCosts(List.of(
                        OptionItemCostDto.builder()
//                                .searchKey("Red")
                                .value("Update Red")
                                .key("Red")
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build())
                )
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Color")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item_cost/testE2EOptionItemCostGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item_cost/emptyE2EOptionGroupItemCostDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Color")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item_cost/testE2EOptionItemCostGroupDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<OptionItemCostGroupDto> constantPayloadDtoList = List.of(
                OptionItemCostGroupDto.builder()
                        .value("Color")
                        .key("Color")
                        .optionItemCosts(List.of(
                                OptionItemCostDto.builder()
//                                        .searchKey("Red")
                                        .value("Red")
                                        .key("Red")
                                        .price(PriceDto.builder()
                                                .amount(10.0)
                                                .currency(CurrencyDto.builder()
                                                        .value("USD")
                                                        .key("USD")
                                                        .build())
                                                .build())
                                        .build(),
                                OptionItemCostDto.builder()
//                                        .searchKey("Blue")
                                        .value("Blue")
                                        .key("Blue")
                                        .price(PriceDto.builder()
                                                .amount(20.0)
                                                .currency(CurrencyDto.builder()
                                                        .value("USD")
                                                        .key("USD")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build(),
                OptionItemCostGroupDto.builder()
                        .value("Modal")
                        .key("Modal")
                        .optionItemCosts(List.of(
                                OptionItemCostDto.builder()
//                                        .searchKey("Modal1")
                                        .value("Modal1")
                                        .key("Modal1")
                                        .price(PriceDto.builder()
                                                .amount(10.0)
                                                .currency(CurrencyDto.builder()
                                                        .value("USD")
                                                        .key("USD")
                                                        .build())
                                                .build())
                                        .build(),
                                OptionItemCostDto.builder()
//                                        .searchKey("Modal2")
                                        .value("Modal2")
                                        .key("Modal2")
                                        .price(PriceDto.builder()
                                                .amount(10.0)
                                                .currency(CurrencyDto.builder()
                                                        .value("USD")
                                                        .key("USD")
                                                        .build())
                                                .build())
                                        .build()
                        ))
                        .build());

        List<OptionItemCostGroupDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<OptionItemCostGroupDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item_cost/testE2EOptionItemCostGroupDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        OptionItemCostGroupDto expected = OptionItemCostGroupDto.builder()
                .value("Color")
                .key("Color")
                .optionItemCosts(List.of(
                        OptionItemCostDto.builder()
//                                .searchKey("Red")
                                .value("Red")
                                .key("Red")
                                .price(PriceDto.builder()
                                        .amount(10.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build(),
                        OptionItemCostDto.builder()
//                                .searchKey("Blue")
                                .value("Blue")
                                .key("Blue")
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build()))
                .build();

        OptionItemCostGroupDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Color")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(OptionItemCostGroupDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private OptionItemCostGroupDto getOptionGroupDto() {
        return OptionItemCostGroupDto.builder()
                .value("Color")
                .key("Color")
                .optionItemCosts(List.of(
                        OptionItemCostDto.builder()
//                                .searchKey(null)
                                .value("Red")
                                .key(null)
                                .price(PriceDto.builder()
                                        .amount(10.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build(),
                        OptionItemCostDto.builder()
//                                .searchKey(null)
                                .value("Blue")
                                .key(null)
                                .price(PriceDto.builder()
                                        .amount(20.0)
                                        .currency(CurrencyDto.builder()
                                                .value("USD")
                                                .key("USD")
                                                .build())
                                        .build())
                                .build()))
                .build();
    }



}
