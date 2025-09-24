
package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.option.OptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.OptionItemDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OptionGroupItemCostControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/option/group";

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/emptyE2EOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/testE2EOptionGroupDataSet.yml", orderBy = "id")
    public void testCreateEntity() {
        OptionGroupDto dto = getOptionGroupDto();
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
    @DataSet(value = "datasets/e2e/item/option_group/testE2EOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/updateE2EOptionGroupDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        OptionGroupDto constantPayloadDto = OptionGroupDto.builder()
                .label("Color")
                .value("Update Color")
//                .optionItems(Set.of())
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
    @DataSet(value = "datasets/e2e/item/option_group/testE2EOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/updateE2EOptionGroupDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        OptionGroupDto constantPayloadDto = OptionGroupDto.builder()
                .label("Color")
                .value("Update Color")
                .optionItems(Set.of())
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
    @DataSet(value = "datasets/e2e/item/option_group/testE2EOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/emptyE2EOptionGroupDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/item/option_group/testE2EOptionGroupDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<OptionGroupDto> constantPayloadDtoList = List.of(
                OptionGroupDto.builder()
                        .label("Color")
                        .value("Color")
                        .optionItems(Set.of())
                        .build(),
                OptionGroupDto.builder()
                        .label("Modal")
                        .value("Modal")
                        .optionItems(Set.of(
                                OptionItemDto.builder()
                                        .label("Modal1")
                                        .value("Modal1")
                                        .build(),
                                OptionItemDto.builder()
                                        .label("Modal2")
                                        .value("Modal2")
                                        .build()
                        ))
                        .build());

        List<OptionGroupDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<OptionGroupDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/testE2EOptionGroupDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        OptionGroupDto expected = OptionGroupDto.builder()
                .label("Color")
                .value("Color")
                .optionItems(Set.of())
                .build();

        OptionGroupDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "Color")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(OptionGroupDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    private OptionGroupDto getOptionGroupDto() {
        return OptionGroupDto.builder()
                .label("Color")
                .value("Color")
                .optionItems(Set.of())
                .build();
    }



}
