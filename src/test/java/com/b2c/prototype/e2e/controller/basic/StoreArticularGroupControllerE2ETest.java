package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.nio.charset.StandardCharsets;

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
        String payload = getExpectedJson("json/storeArticularGroup/input/createStoreArticularGroupRequest.json");

        webTestClient.post()
                .uri(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_group/addE2EStoreArticularItemsDataSet.yml",
            orderBy = {"value", "char_sequence_code", "product_name"},
            ignoreCols = {
                    "id", "key", "articular_group_uniq_id", "articular_uniq_id", "date_of_create", "store_uniq_id",
                    "articular_item_id", "price_id", "full_price_id", "total_price_id", "discount_id", "address_id", "store_id", "articular_item_quantity_id", "quantity", "store_name"
            })
    @Sql(statements = {
            "ALTER SEQUENCE articular_group_id_seq RESTART WITH 2",
            "ALTER SEQUENCE articular_item_id_seq RESTART WITH 4",
            "ALTER SEQUENCE item_id_seq RESTART WITH 3",
            "ALTER SEQUENCE address_id_seq RESTART WITH 3",
            "ALTER SEQUENCE store_id_seq RESTART WITH 3",
            "ALTER SEQUENCE discount_group_id_seq RESTART WITH 3",
            "ALTER SEQUENCE discount_id_seq RESTART WITH 4",
            "ALTER SEQUENCE price_id_seq RESTART WITH 8",
            "ALTER SEQUENCE option_group_id_seq RESTART WITH 4",
            "ALTER SEQUENCE option_item_id_seq RESTART WITH 5",
            "ALTER SEQUENCE option_item_cost_id_seq RESTART WITH 4",
            "ALTER SEQUENCE store_general_board_id_seq RESTART WITH 3",

            "ALTER SEQUENCE articular_item_quantity_id_seq RESTART WITH 6",
            "ALTER SEQUENCE articular_stock_id_seq RESTART WITH 6",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testAddArticularItemEntity() {
        String payload = getExpectedJson("json/storeArticularGroup/input/addStoreArticularItemRequest.json");

        webTestClient.post()
                .uri(URL_TEMPLATE + "/item")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_group/updateE2EStoreArticularGroupDataSet.yml",
        orderBy = {"value", "char_sequence_code", "product_name"},
        ignoreCols = {
            "id", "key", "articular_group_uniq_id", "articular_uniq_id", "date_of_create", "store_uniq_id",
                    "articular_item_id", "price_id", "full_price_id", "total_price_id", "discount_id", "address_id", "store_id", "articular_item_quantity_id", "quantity", "store_name"
        })
    @Sql(statements = {
            "ALTER SEQUENCE articular_group_id_seq RESTART WITH 3",
            "ALTER SEQUENCE articular_item_id_seq RESTART WITH 4",
            "ALTER SEQUENCE address_id_seq RESTART WITH 3",
            "ALTER SEQUENCE discount_group_id_seq RESTART WITH 3",
            "ALTER SEQUENCE discount_id_seq RESTART WITH 4",
            "ALTER SEQUENCE price_id_seq RESTART WITH 8",
            "ALTER SEQUENCE option_group_id_seq RESTART WITH 4",
            "ALTER SEQUENCE option_item_id_seq RESTART WITH 5",
            "ALTER SEQUENCE option_item_cost_id_seq RESTART WITH 4",
            "ALTER SEQUENCE store_general_board_id_seq RESTART WITH 3",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntity() {
        String payload = getExpectedJson("json/storeArticularGroup/input/updateStoreArticularGroupRequest.json");

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("tenant", "Global")
                        .queryParam("articularGroupId", "111")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
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
    @ExpectedDataSet(value = "datasets/e2e/item/articular_group/deleteE2EStoreArticularItemDataSet.yml", orderBy = "id")
    public void testDeleteArticularItemEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE + "/item")
                        .queryParam("tenant", "Global")
                        .queryParam("articularGroupId", "111")
                        .queryParam("articularId", "articularId3")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/articular_group/deleteE2EStoreArticularItemForcedDataSet.yml", orderBy = "id")
    public void testDeleteArticularItemEntityForced() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE + "/item")
                        .queryParam("tenant", "Global")
                        .queryParam("articularGroupId", "111")
                        .queryParam("articularId", "articularId3")
                        .queryParam("isForced", "true")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        String expectedJson = getExpectedJson("json/storeArticularGroup/output/storeArticularGroupResponseList.json");

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
                .json(expectedJson);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        String expectedJson = getExpectedJson("json/storeArticularGroup/output/storeArticularGroupResponse.json");

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
}
