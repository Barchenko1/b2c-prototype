package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ItemTypeControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/item/type";

    @Test
    @DataSet(value = "datasets/e2e/item/item_type/emptyE2EItemTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/item_type/testE2EItemTypeDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        ConstantPayloadDto dto = getConstantPayloadDto();
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
    @DataSet(value = "datasets/e2e/item/item_type/testE2EItemTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/item_type/updateE2EItemTypeDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update Clothes1")
                .key("Update Clothes1")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Clothes1")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/item_type/testE2EItemTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/item_type/emptyE2EItemTypeDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Clothes2")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/item_type/testE2EItemTypeDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<ItemTypeDto> constantPayloadDtoList = List.of(
                ItemTypeDto.builder()
                        .value("Clothes1")
                        .key("Clothes1")
                        .build(),
                ItemTypeDto.builder()
                        .value("Clothes2")
                        .key("Clothes2")
                        .build());

        List<ItemTypeDto> actual = webTestClient.get()
                        .uri(URL_TEMPLATE + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<ItemTypeDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/item_type/testE2EItemTypeDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        ItemTypeDto expected = ItemTypeDto.builder()
                .value("Clothes2")
                .key("Clothes2")
                .build();

        ItemTypeDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Clothes2")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(ItemTypeDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .value("Clothes2")
                .key("Clothes2")
                .build();
    }

}
