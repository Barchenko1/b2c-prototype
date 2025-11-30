package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OptionItemGroupControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/option/group/item";

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item/emptyE2EOptionItemGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item/testE2EOptionItemGroupDataSet.yml", orderBy = "id",
    ignoreCols = {"key"})
    @Sql(statements = {
            "ALTER SEQUENCE option_item_id_seq RESTART WITH 3",
            "ALTER SEQUENCE option_group_id_seq RESTART WITH 2",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    @DataSet(value = "datasets/e2e/item/option_group/option_item/testE2EOptionItemGroupDataSet.yml", cleanBefore = true, disableConstraints = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item/updateE2EOptionItemGroupDataSetMore.yml", orderBy = {"id"})
    @Sql(statements = {
            "ALTER SEQUENCE option_item_id_seq RESTART WITH 5",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntityMore() {
        OptionItemGroupDto constantPayloadDto = OptionItemGroupDto.builder()
                .value("Update Color")
                .key("Update Color")
                .optionItems(List.of(
                        OptionItemDto.builder()
//                                .searchKey("Red")
                                .value("Update Red")
                                .key("Red")
                                .build(),
                        OptionItemDto.builder()
//                                .searchKey(null)
                                .value(null)
                                .key("Yellow")
                                .build(),
                        OptionItemDto.builder()
//                                .searchKey(null)
                                .value(null)
                                .key("White")
                                .build()
                        ))
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Color")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/option_group/option_item/testE2EOptionItemGroupDataSet.yml", cleanBefore = true, disableConstraints = true)
    @ExpectedDataSet(value = "datasets/e2e/item/option_group/option_item/updateE2EOptionItemGroupDataSetLess.yml", orderBy = "id")
    public void testUpdateEntityLess() {
        OptionItemGroupDto constantPayloadDto = OptionItemGroupDto.builder()
                .value("Update Color")
                .key("Update Color")
                .optionItems(List.of(
                        OptionItemDto.builder()
                                .value("Update Red")
                                .key("Red")
                                .build()
                ))
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Color")
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
                        .queryParam("key", "Color")
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
                        .value("Color")
                        .key("Color")
                        .optionItems(List.of(
                                OptionItemDto.builder()
//                                        .searchKey("Red")
                                        .value("Red")
                                        .key("Red")
                                        .build(),
                                OptionItemDto.builder()
//                                        .searchKey("Blue")
                                        .value("Blue")
                                        .key("Blue")
                                        .build()))
                        .build(),
                OptionItemGroupDto.builder()
                        .value("Modal")
                        .key("Modal")
                        .optionItems(List.of(
                                OptionItemDto.builder()
//                                        .searchKey("Modal1")
                                        .value("Modal1")
                                        .key("Modal1")
                                        .build(),
                                OptionItemDto.builder()
//                                        .searchKey("Modal2")
                                        .value("Modal2")
                                        .key("Modal2")
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
                .value("Color")
                .key("Color")
                .optionItems(List.of(
                        OptionItemDto.builder()
//                                .searchKey("Red")
                                .value("Red")
                                .key("Red")
                                .build(),
                        OptionItemDto.builder()
//                                .searchKey("Blue")
                                .value("Blue")
                                .key("Blue")
                                .build()))
                .build();

        OptionItemGroupDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Color")
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
                .build();
    }



}
