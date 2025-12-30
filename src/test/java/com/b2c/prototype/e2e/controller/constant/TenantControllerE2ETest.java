package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

public class TenantControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/tenant";

    @Test
    @DataSet(value = "datasets/e2e/tenant/emptyE2ETenantDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", orderBy = "id")
    @Sql(statements = {
            "ALTER SEQUENCE currency_id_seq RESTART WITH 2",
            "ALTER SEQUENCE tenant_id_seq RESTART WITH 2",
            "ALTER SEQUENCE language_id_seq RESTART WITH 2",
            "ALTER SEQUENCE store_general_board_id_seq RESTART WITH 2"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCreateEntity() {
        String request = getExpectedJson("json/tenant/input/tenantRequest.json");

        webTestClient.post()
                .uri(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/tenant/updateE2ETenantMoreDataSet.yml", orderBy = "id")
    @Sql(statements = {
            "ALTER SEQUENCE language_id_seq RESTART WITH 4",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntityMore() {
        String request = getExpectedJson("json/tenant/input/tenantUpdateRequestMore.json");
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("code", "Global")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/tenant/updateE2ETenantLessDataSet.yml", orderBy = "id")
    @Sql(statements = {
            "ALTER SEQUENCE language_id_seq RESTART WITH 4",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntityLess() {
        String request = getExpectedJson("json/tenant/input/tenantUpdateRequestLess.json");
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("code", "Global")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/tenant/emptyE2ETenantDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("code", "Global")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        String response = getExpectedJson("json/tenant/output/tenantListResponse.json");

        webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .json(response);
    }

    @Test
    @DataSet(value = "datasets/e2e/tenant/testE2ETenantDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        String response = getExpectedJson("json/tenant/output/tenantResponse.json");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("code", "Global")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .json(response);
    }

}
