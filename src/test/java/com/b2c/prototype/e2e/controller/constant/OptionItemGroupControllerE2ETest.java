
package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OptionItemGroupControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/option/group/item";

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item/emptyE2EOptionItemGroupDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE option_item RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE option_group RESTART IDENTITY CASCADE"
    })
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item/testE2EOptionItemGroupDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        OptionItemGroupDto dto = getOptionGroupDto();
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
    @DataSet(value = "datasets/e2e/item/option_group/option_item/testUpdateE2EOptionItemGroupDataSet.yml", cleanBefore = true, disableConstraints = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item/updateE2EOptionItemGroupDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        OptionItemGroupDto constantPayloadDto = OptionItemGroupDto.builder()
                .label("Update Color")
                .value("Update Color")
                .optionItems(List.of(
                        OptionItemDto.builder()
                                .searchValue("Red")
                                .label("Update Red")
                                .value("Update Red")
                                .build(),
                        OptionItemDto.builder()
                                .label("Yellow")
                                .value("Yellow")
                                .build()
                        ))
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Color")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item/testUpdateE2EOptionItemGroupDataSet.yml", cleanBefore = true, disableConstraints = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item/updateE2EOptionItemGroupDataSet.yml", orderBy = "id", ignoreCols = {"id"})
    public void testPatchEntity() {
        OptionItemGroupDto constantPayloadDto = OptionItemGroupDto.builder()
                .label("Update Color")
                .value("Update Color")
                .optionItems(List.of(
                        OptionItemDto.builder()
                                .searchValue("Red")
                                .label("Update Red")
                                .value("Update Red")
                                .build(),
                        OptionItemDto.builder()
                                .label("Yellow")
                                .value("Yellow")
                                .build()
                ))
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Color")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item/testE2EOptionItemGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item/emptyE2EOptionItemGroupDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Color")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item/testE2EOptionItemGroupDataSet.yml", cleanBefore = true, tableOrdering = {"id"})
    public void testGetEntities() {
        List<OptionItemGroupDto> constantPayloadDtoList = List.of(
                OptionItemGroupDto.builder()
                        .label("Color")
                        .value("Color")
                        .optionItems(List.of(
                                OptionItemDto.builder()
                                        .searchValue("Red")
                                        .label("Red")
                                        .value("Red")
                                        .build(),
                                OptionItemDto.builder()
                                        .searchValue("Blue")
                                        .label("Blue")
                                        .value("Blue")
                                        .build()))
                        .build(),
                OptionItemGroupDto.builder()
                        .label("Modal")
                        .value("Modal")
                        .optionItems(List.of(
                                OptionItemDto.builder()
                                        .searchValue("Modal1")
                                        .label("Modal1")
                                        .value("Modal1")
                                        .build(),
                                OptionItemDto.builder()
                                        .searchValue("Modal2")
                                        .label("Modal2")
                                        .value("Modal2")
                                        .build()
                        ))
                        .build());

        List<OptionItemGroupDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<OptionItemGroupDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item/testE2EOptionItemGroupDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        OptionItemGroupDto expected = OptionItemGroupDto.builder()
                .label("Color")
                .value("Color")
                .optionItems(List.of(
                        OptionItemDto.builder()
                                .searchValue("Red")
                                .label("Red")
                                .value("Red")
                                .build(),
                        OptionItemDto.builder()
                                .searchValue("Blue")
                                .label("Blue")
                                .value("Blue")
                                .build()))
                .build();

        OptionItemGroupDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Color")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(OptionItemGroupDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private OptionItemGroupDto getOptionGroupDto() {
        return OptionItemGroupDto.builder()
                .label("Color")
                .value("Color")
                .optionItems(List.of(
                        OptionItemDto.builder()
                                .label("Red")
                                .value("Red")
                                .build(),
                        OptionItemDto.builder()
                                .label("Blue")
                                .value("Blue")
                                .build()))
                .build();
    }



}
