package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StoreControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/item/store";

    @Test
    @DataSet(value = "datasets/e2e/store/store/emptyE2EStoreDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", orderBy = "id",
            ignoreCols = {"store_uniq_id"})
    @Sql(statements = {
            "ALTER SEQUENCE store_id_seq RESTART WITH 2",
            "ALTER SEQUENCE articular_stock_id_seq RESTART WITH 2",
            "ALTER SEQUENCE articular_item_quantity_id_seq RESTART WITH 3",
            "ALTER SEQUENCE address_id_seq RESTART WITH 2",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCreateEntity() {
        StoreDto dto = getStoreDto2();
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
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/store/updateE2EStoreDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        StoreDto dto = getUpdateStoreDto2();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("store", "store2")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/store/emptyE2EStoreDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("store", "store2")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    public void testGetEntitiesByRegion() {
        StoreDto dto2 = getStoreDto2();
        dto2.setStoreId("store2");
        List<StoreDto> expected = List.of(
                dto2
        );

        List<StoreDto> actual =
                webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(URL_TEMPLATE + "/all")
                                .queryParam("region", "Global")
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<StoreDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    public void testGetEntitiesByRegionAndCountry() {
        StoreDto dto2 = getStoreDto2();
        dto2.setStoreId("store2");
        List<StoreDto> expected = List.of(
                dto2
        );

        List<StoreDto> actual =
                webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(URL_TEMPLATE + "/all")
                                .queryParam("region", "Global")
                                .queryParam("country", "USA")
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<StoreDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    public void testGetEntitiesByRegionAndCountryAndStore() {
        StoreDto dto2 = getStoreDto2();
        dto2.setStoreId("store2");
        List<StoreDto> expected = List.of(
                dto2
        );

        List<StoreDto> actual =
                webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(URL_TEMPLATE + "/all")
                                .queryParam("region", "Global")
                                .queryParam("country", "USA")
                                .queryParam("city", "city")
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<StoreDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        StoreDto expected = getStoreDto2();
        expected.setStoreId("store2");

        StoreDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .pathSegment("store2")
                        .queryParam("region", "Global")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(StoreDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private StoreDto getStoreDto2() {
        return StoreDto.builder()
                .storeName("store2")
                .isActive(false)
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
                        .zipCode("90000")
                        .build())
                .region("Global")
                .build();
    }

    private StoreDto getUpdateStoreDto2() {
        return StoreDto.builder()
                .storeName("Update store2")
                .isActive(true)
                .address(AddressDto.builder()
                        .country(CountryDto.builder()
                                .value("GERMANY")
                                .key("GERMANY")
                                .build())
                        .city("Update city")
                        .street("Update street")
                        .buildingNumber("1010")
                        .florNumber(99)
                        .apartmentNumber(201201)
                        .zipCode("900009")
                        .build())
                .region("DE")
                .build();
    }

}
