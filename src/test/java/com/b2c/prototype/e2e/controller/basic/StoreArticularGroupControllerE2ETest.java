package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.constant.CountType;
import com.b2c.prototype.modal.dto.payload.constant.ArticularStatusDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryCascade;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemAssignmentDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularStockQuantityDto;
import com.b2c.prototype.modal.dto.payload.item.GroupOptionKeys;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.item.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.StoreRequestDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StoreArticularGroupControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/item/store/group";

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/emptyE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", orderBy = "id")
    @Sql(statements = {
            "ALTER SEQUENCE articular_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE articular_item_id_seq RESTART WITH 2",
            "ALTER SEQUENCE store_id_seq RESTART WITH 2",
            "ALTER SEQUENCE articular_stock_id_seq RESTART WITH 2",
            "ALTER SEQUENCE articular_item_quantity_id_seq RESTART WITH 3",
            "ALTER SEQUENCE address_id_seq RESTART WITH 2",
            "ALTER SEQUENCE discount_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE discount_id_seq RESTART WITH 2",
            "ALTER SEQUENCE price_id_seq RESTART WITH 2",
            "ALTER SEQUENCE option_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE option_item_id_seq RESTART WITH 3",
            "ALTER SEQUENCE option_item_cost_id_seq RESTART WITH 2",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCreateEntity() {
        StoreArticularGroupRequestDto dto = getStoreArticularGroupRequestDto();
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
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_group/updateE2EStoreArticularGroupDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        StoreArticularGroupRequestDto dto = getStoreArticularGroupRequestDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Apple")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_group/emptyE2EStoreArticularGroupDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Apple")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<StoreArticularGroupRequestDto> expected = List.of();

        List<StoreArticularGroupRequestDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<StoreArticularGroupRequestDto>>() {
                        })
                        .returnResult()
                        .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        StoreArticularGroupRequestDto expected = getStoreArticularGroupRequestDto();

        StoreArticularGroupRequestDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Apple")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(StoreArticularGroupRequestDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private StoreArticularGroupRequestDto getStoreArticularGroupRequestDto() {
        return StoreArticularGroupRequestDto.builder()
                .region("Global")
                .discountGroupList(List.of(
                        DiscountGroupDto.builder()
                                .key("Global_group")
                                .value("Global_group")
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
                                .build()
                ))
                .optionItemGroupList(List.of(
                        OptionItemGroupDto.builder()
                                .value("Color")
                                .key("Color")
                                .optionItems(List.of(
                                        OptionItemDto.builder()
                                                .value("Red")
                                                .key("Red")
                                                .build(),
                                        OptionItemDto.builder()
                                                .value("Blue")
                                                .key("Blue")
                                                .build()))
                                .build()
                ))
                .optionItemCostGroupList(List.of(
                        OptionItemCostGroupDto.builder()
                                .value("Color")
                                .key("Color")
                                .optionItemCosts(List.of(
                                        OptionItemCostDto.builder()
                                                .value("Red")
                                                .price(PriceDto.builder()
                                                        .amount(20.0)
                                                        .currency(CurrencyDto.builder()
                                                                .key("USD")
                                                                .value("USD")
                                                                .build())
                                                        .build())
                                                .key("Red")
                                                .build(),
                                        OptionItemCostDto.builder()
                                                .value("Blue")
                                                .price(PriceDto.builder()
                                                        .amount(30.0)
                                                        .currency(CurrencyDto.builder()
                                                                .key("USD")
                                                                .value("USD")
                                                                .build())
                                                        .build())
                                                .key("Blue")
                                                .build()))
                                .build()
                ))
                .articularGroup(getArticularGroupDto())
                .articularItems(Map.of(
                        "item1", getArticularItemAssignmentDto1(),
                        "item2", getArticularItemAssignmentDto2()
                ))
                .stores(Map.of(
                        "item1", List.of(getStoreRequestDto1(), getStoreRequestDto2()),
                        "item2", List.of(getStoreRequestDto1())
                ))

                .build();
    }

    private ArticularGroupDto getArticularGroupDto() {
        return ArticularGroupDto.builder()
                .category(CategoryCascade.builder()
                        .key("Laptop")
                        .value("Laptop")
                        .build())
                .description(Map.of("desc1","desc1","desc2","desc2"))
                .build();

    }

    private ArticularItemAssignmentDto getArticularItemAssignmentDto1() {
        return ArticularItemAssignmentDto.builder()
                .articularId("articularId1")
                .productName("productName1")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .fullPrice(getPriceDto(50))
                .totalPrice(getPriceDto(30))
                .status(ArticularStatusDto.builder()
                        .value("New")
                        .key("New")
                        .build())
                .optionKeys(List.of(
                        GroupOptionKeys.builder()
                                .groupKey("Color")
                                .optionKeys(List.of("Red", "Blue"))
                                .build()
                ))
                .optionCostKeys(List.of(
                        GroupOptionKeys.builder()
                                .groupKey("Color")
                                .optionKeys(List.of("Red", "Blue"))
                                .build()
                ))
                .discountKey(
                        GroupOptionKeys.builder()
                                .groupKey("Global_group")
                                .optionKeys(List.of("abc2"))
                                .build()
                )
                .build();
    }

    private ArticularItemAssignmentDto getArticularItemAssignmentDto2() {
        return ArticularItemAssignmentDto.builder()
                .articularId("articularId2")
                .productName("productName2")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .fullPrice(getPriceDto(50))
                .totalPrice(getPriceDto(30))
                .status(ArticularStatusDto.builder()
                        .value("Common")
                        .key("Common")
                        .build())
                .optionKeys(List.of(
                        GroupOptionKeys.builder()
                                .groupKey("Color")
                                .optionKeys(List.of("Red", "Blue"))
                                .build()
                ))
                .optionCostKeys(List.of(
                        GroupOptionKeys.builder()
                                .groupKey("Color")
                                .optionKeys(List.of("Red", "Blue"))
                                .build()
                ))
                .discountKey(
                        GroupOptionKeys.builder()
                                .groupKey("Global_group")
                                .optionKeys(List.of("abc3"))
                                .build()
                )
                .build();
    }

    private PriceDto getPriceDto(double amount) {
        return PriceDto.builder()
                .amount(amount)
                .currency(CurrencyDto.builder()
                        .key("USD")
                        .value("USD")
                        .build())
                .build();
    }

    private StoreRequestDto getStoreRequestDto1() {
        return StoreRequestDto.builder()
                .storeName("Store1")
                .address(AddressDto.builder()
                        .country(CountryDto.builder()
                                .value("USA")
                                .key("USA")
                                .build())
                        .city("city")
                        .street("street")
                        .buildingNumber("10")
                        .florNumber(9)
                        .apartmentNumber(201)
                        .zipCode("900001")
                        .build())
                .stock(Map.of(
                        "item1", getArticularStockQuantityDto1(),
                        "item2", getArticularStockQuantityDto2()
                ))
                .build();
    }

    private StoreRequestDto getStoreRequestDto2() {
        return StoreRequestDto.builder()
                .storeName("Store2")
                .address(AddressDto.builder()
                        .country(CountryDto.builder()
                                .value("USA")
                                .key("USA")
                                .build())
                        .city("city2")
                        .street("street2")
                        .buildingNumber("10")
                        .florNumber(9)
                        .apartmentNumber(201)
                        .zipCode("900002")
                        .build())
                .stock(Map.of(
                        "item1", getArticularStockQuantityDto1()
                ))
                .build();
    }

    private ArticularStockQuantityDto getArticularStockQuantityDto1() {
        return ArticularStockQuantityDto.builder()
                .availabilityStatus(AvailabilityStatusDto.builder()
                        .key("Available")
                        .value("Available")
                        .build())
                .countType(CountType.LIMITED.name())
                .quantity(5)
                .build();
    }

    private ArticularStockQuantityDto getArticularStockQuantityDto2() {
        return ArticularStockQuantityDto.builder()
                .availabilityStatus(AvailabilityStatusDto.builder()
                        .key("Available")
                        .value("Available")
                        .build())
                .countType(CountType.LIMITED.name())
                .quantity(2)
                .build();
    }

}
