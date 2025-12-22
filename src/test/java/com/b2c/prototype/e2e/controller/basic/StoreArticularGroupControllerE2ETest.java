package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.constant.CountType;
import com.b2c.prototype.modal.dto.payload.constant.ArticularStatusDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryCascade;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularFullDescription;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ItemDto;
import com.b2c.prototype.modal.dto.payload.item.request.ArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.request.ArticularItemAssignmentDto;
import com.b2c.prototype.modal.dto.payload.item.response.ArticularStockQuantityDto;
import com.b2c.prototype.modal.dto.payload.item.response.GroupOptionKeys;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscount;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscountGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemCostGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroup;
import com.b2c.prototype.modal.dto.payload.item.response.ArticularGroupResponseDto;
import com.b2c.prototype.modal.dto.payload.item.response.StoreRequestDto;
import com.b2c.prototype.modal.dto.payload.item.response.StoreArticularGroupResponseDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.store.ArticularStockItemDto;
import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import com.b2c.prototype.modal.dto.payload.store.StoreArticularStockDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.payload.store.StoreGeneralBoardDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.b2c.prototype.util.Converter.getLocalDateTime;

class StoreArticularGroupControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/item/store/group";

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/emptyE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml",
            orderBy = {"value", "char_sequence_code", "product_name"},
            ignoreCols = {
                "id", "key", "articular_group_uniq_id", "articular_uniq_id", "date_of_create", "store_uniq_id",
                "articular_item_id", "price_id", "full_price_id", "total_price_id", "discount_id", "address_id", "store_id", "articular_item_quantity_id", "quantity", "store_name"
    })
    @Sql(statements = {
            "ALTER SEQUENCE articular_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE articular_item_id_seq RESTART WITH 2",
            "ALTER SEQUENCE address_id_seq RESTART WITH 2",
            "ALTER SEQUENCE discount_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE discount_id_seq RESTART WITH 2",
            "ALTER SEQUENCE price_id_seq RESTART WITH 2",
            "ALTER SEQUENCE option_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE option_item_id_seq RESTART WITH 3",
            "ALTER SEQUENCE option_item_cost_id_seq RESTART WITH 2",
            "ALTER SEQUENCE store_general_board_id_seq RESTART WITH 2",
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
                        .queryParam("tenant", "Global")
                        .queryParam("articularGroupId", "111")
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
                        .queryParam("tenant", "Global")
                        .queryParam("articularGroupId", "111")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        String expectedJson = getExpectedJson("datasets/expected/storeArticularGroupResponseList.json");

        webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(result -> {
                    byte[] body = result.getResponseBody();
                    if (body != null) {
                        System.out.println("=== RESPONSE BODY ===");
                        System.out.println(new String(body, StandardCharsets.UTF_8));
                        System.out.println("=====================");
                    }
                })
                .json(expectedJson);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        String expectedJson = getExpectedJson("datasets/expected/storeArticularGroupResponse.json");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("tenant", "Global")
                        .queryParam("articularGroupId", "111")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(result -> {
                    byte[] body = result.getResponseBody();
                    if (body != null) {
                        System.out.println("=== RESPONSE BODY ===");
                        System.out.println(new String(body, StandardCharsets.UTF_8));
                        System.out.println("=====================");
                    }
                })
                .json(expectedJson);
    }

    private StoreArticularGroupRequestDto getStoreArticularGroupRequestDto() {
        return StoreArticularGroupRequestDto.builder()
                .tenantId("Global")
                .discountGroups(Map.of(
                        "discountGroup1",
                        StoreDiscountGroup.builder()
                                .key(null)
                                .value("Global_group")
                                .discounts(Map.of(
                                        "discount1",
                                        StoreDiscount.builder()
                                                .charSequenceCode("abc2")
                                                .amount(10)
                                                .currency(CurrencyDto.builder()
                                                        .key("USD")
                                                        .value("USD")
                                                        .build())
                                                .isActive(false)
                                                .build(),
                                        "discount2",
                                        StoreDiscount.builder()
                                                .charSequenceCode("abc3")
                                                .amount(10)
                                                .currency(null)
                                                .isActive(false)
                                                .isPercent(true)
                                                .build()))
                                .build()
                ))
                .optionItemGroups(Map.of(
                        "optionGroup1",
                        StoreOptionItemGroup.builder()
                                .value("Color")
                                .key(null)
                                .optionItems(Map.of(
                                        "option1",
                                        OptionItemDto.builder()
                                                .value("Red")
                                                .key(null)
                                                .build(),
                                        "option2",
                                        OptionItemDto.builder()
                                                .value("Blue")
                                                .key(null)
                                                .build()))
                                .build()
                ))
                .optionItemCostGroups(Map.of(
                        "optionCostGroup1",
                        StoreOptionItemCostGroup.builder()
                                .value("Color")
                                .key(null)
                                .costOptions(Map.of(
                                        "optionCost1",
                                        OptionItemCostDto.builder()
                                                .value("Red")
                                                .price(PriceDto.builder()
                                                        .amount(30.0)
                                                        .currency(CurrencyDto.builder()
                                                                .key("USD")
                                                                .value("USD")
                                                                .build())
                                                        .build())
                                                .key("Red")
                                                .build(),
                                        "optionCost2",
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
                .storeGroup(Map.of(
                        "store1", getStoreRequestDto1(),
                        "store2", getStoreRequestDto2()
                ))
                .articularGroup(getArticularGroupDto())
                .build();
    }

    private ArticularGroupRequestDto getArticularGroupDto() {
        return ArticularGroupRequestDto.builder()
                .category(CategoryCascade.builder()
                        .key("Laptop")
                        .value("Laptop")
                        .build())
                .description(new LinkedHashMap(){{
                    put("desc1", "desc1");
                    put("desc2", "desc2");
                }})
                .articularItems(Map.of(
                        "item1", getArticularItemAssignmentDto1(),
                        "item2", getArticularItemAssignmentDto2()
                ))
                .build();

    }

    private ArticularItemAssignmentDto getArticularItemAssignmentDto1() {
        return ArticularItemAssignmentDto.builder()
                .articularId("articularId2")
                .productName("productName2")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .fullPrice(getPriceDto(50))
                .totalPrice(getPriceDto(30))
                .status(ArticularStatusDto.builder()
                        .value("New")
                        .key("New")
                        .build())
                .optionKeys(List.of(
                        GroupOptionKeys.builder()
                                .groupKey("optionGroup1")
                                .optionKeys(List.of("option1", "option2"))
                                .build()
                ))
                .optionCostKeys(List.of(
                        GroupOptionKeys.builder()
                                .groupKey("optionCostGroup1")
                                .optionKeys(List.of("optionCost1", "optionCost2"))
                                .build()
                ))
                .discountKey(
                        GroupOptionKeys.builder()
                                .groupKey("discountGroup1")
                                .optionKeys(List.of("discount1"))
                                .build()
                )
                .storeKey(List.of("store1", "store2"))
                .build();
    }

    private ArticularItemAssignmentDto getArticularItemAssignmentDto2() {
        return ArticularItemAssignmentDto.builder()
                .articularId("articularId3")
                .productName("productName3")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .fullPrice(getPriceDto(50))
                .totalPrice(getPriceDto(30))
                .status(ArticularStatusDto.builder()
                        .value("New")
                        .key("New")
                        .build())
                .optionKeys(List.of(
                        GroupOptionKeys.builder()
                                .groupKey("optionGroup1")
                                .optionKeys(List.of("option1", "option2"))
                                .build()
                ))
                .optionCostKeys(List.of(
                        GroupOptionKeys.builder()
                                .groupKey("optionCostGroup1")
                                .optionKeys(List.of("optionCost1", "optionCost2"))
                                .build()
                ))
                .discountKey(
                        GroupOptionKeys.builder()
                                .groupKey("discountGroup1")
                                .optionKeys(List.of("discount2"))
                                .build()
                )
                .storeKey(List.of("store1"))
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
                        .apartmentNumber(101)
                        .zipCode("90000")
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
                        .city("city")
                        .street("street")
                        .buildingNumber("10")
                        .florNumber(9)
                        .apartmentNumber(101)
                        .zipCode("90000")
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
