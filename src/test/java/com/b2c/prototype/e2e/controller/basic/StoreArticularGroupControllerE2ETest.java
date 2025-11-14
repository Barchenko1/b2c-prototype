package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StoreArticularGroupControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/item/articular/group";

    @Test
    @DataSet(value = "datasets/e2e/store/articular_group/emptyE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/articular_group/testE2EStoreArticularGroupDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        ArticularGroupDto dto = getArticleGroupDto();
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
    @DataSet(value = "datasets/e2e/store/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/articular_group/updateE2EStoreArticularGroupDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ArticularGroupDto dto = getArticleGroupDto();
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
    @DataSet(value = "datasets/e2e/store/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/store/articular_group/emptyE2EStoreArticularGroupDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/store/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<ArticularGroupDto> expected = List.of();

        List<ArticularGroupDto> actual =
                webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<ArticularGroupDto>>() {
                        })
                        .returnResult()
                        .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/e2e/store/articular_group/testE2EStoreArticularGroupDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        ArticularGroupDto expected = getArticleGroupDto();

        ArticularGroupDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Apple")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(ArticularGroupDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private ArticularGroupDto getArticleGroupDto() {
        return ArticularGroupDto.builder()

                .build();
    }

}
