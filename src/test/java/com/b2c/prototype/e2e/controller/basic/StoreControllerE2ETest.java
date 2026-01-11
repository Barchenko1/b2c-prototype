package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.nio.charset.StandardCharsets;

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
        String payload = getExpectedJson("json/store/input/storeDto.json");

        webTestClient.post()
                .uri(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/store/updateE2EStoreDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        String payload = getExpectedJson("json/store/input/updateStoreDto.json");

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("tenant", "Global")
                        .queryParam("store", "store2")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
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
                        .queryParam("tenant", "Global")
                        .queryParam("store", "store2")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    public void testGetEntitiesByRegion() {
        String payload = getExpectedJson("json/store/output/responseStoreDtoList.json");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE + "/all")
                        .queryParam("tenant", "Global")
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
                .json(payload);
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    public void testGetEntitiesByRegionAndCountry() {
        String payload = getExpectedJson("json/store/output/responseStoreDtoList.json");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE + "/all")
                        .queryParam("tenant", "Global")
                        .queryParam("country", "USA")
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
                .json(payload);
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    public void testGetEntitiesByRegionAndCountryAndStore() {
        String payload = getExpectedJson("json/store/output/responseStoreDtoList.json");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE + "/all")
                        .queryParam("tenant", "Global")
                        .queryParam("country", "USA")
                        .queryParam("city", "city")
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
                .json(payload);
    }

    @Test
    @DataSet(value = "datasets/e2e/store/store/testE2EStoreDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        String payload = getExpectedJson("json/store/output/responseStoreDto.json");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .pathSegment("store2")
                        .queryParam("tenant", "Global")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .json(payload);
    }
}
