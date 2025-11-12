package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CategoryCascade;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CategoryControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/item/category";

    @Test
    @DataSet(value = "datasets/e2e/item/category/emptyE2ECategoryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/category/testE2ECategoryDataSet.yml",
            orderBy = "id",
            ignoreCols = {"key"})
    @Sql(statements = {
            "ALTER SEQUENCE category_id_seq RESTART WITH 6",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCreateEntity() {
        CategoryDto dto = getCategoryDto();
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
    @DataSet(value = "datasets/e2e/item/category/testE2ECategoryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/category/updateE2ECategoryDataSetMore.yml", orderBy = "id")
    @Sql(statements = {
            "ALTER SEQUENCE category_id_seq RESTART WITH 22",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntityMore() {
        CategoryDto dto = getUpdateCategoryDtoMore();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("category", "Apple")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/category/testE2ECategoryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/category/updateE2ECategoryDataSetLess.yml", orderBy = "id")
    public void testUpdateEntityLess() {
        CategoryDto dto = getUpdateCategoryDtoLess();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("category", "Apple")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/category/testE2ECategoryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/category/updateE2ECategoryDataSetReplace.yml",
            orderBy = "id",
            ignoreCols = {"key"})
    @Sql(statements = {
            "ALTER SEQUENCE category_id_seq RESTART WITH 22",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateEntityReplace() {
        CategoryDto dto = getUpdateCategoryDtoReplace();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("category", "Apple")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/category/testE2ECategoryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/item/category/emptyE2ECategoryDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("category", "Apple")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/item/category/testE2ECategoryDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<CategoryDto> constantPayloadDtoList = List.of(
                getCategoryDto2()
        );

        List<CategoryDto> actual =
                webTestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path(URL_TEMPLATE + "/all")
                                .queryParam("region", "Global")
                                .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                        .expectBody(new ParameterizedTypeReference<List<CategoryDto>>() {})
                        .returnResult()
                        .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/item/category/testE2ECategoryDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        CategoryDto expected = getCategoryDto2();

        CategoryDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("region", "Global")
                        .queryParam("category", "Apple")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(CategoryDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private CategoryDto getCategoryDto() {
        return CategoryDto.builder()
                .region("Global")
                .category(
                        CategoryCascade.builder()
                                .value("Apple")
                                .childList(List.of(
                                                CategoryCascade.builder()
                                                        .value("Mac")
                                                        .childList(List.of(
                                                                CategoryCascade.builder()
                                                                        .value("Macbook air")
                                                                        .build(),
                                                                CategoryCascade.builder()
                                                                        .value("Macbook pro")
                                                                        .build()
                                                        ))
                                                        .build(),
                                                CategoryCascade.builder()
                                                        .value("IPhone")
                                                        .childList(List.of(
                                                                CategoryCascade.builder()
                                                                        .value("IPhone air")
                                                                        .build(),
                                                                CategoryCascade.builder()
                                                                        .value("IPhone pro")
                                                                        .build(),
                                                                CategoryCascade.builder()
                                                                        .value("IPhone 17")
                                                                        .build(),
                                                                CategoryCascade.builder()
                                                                        .value("IPhone 16")
                                                                        .build()
                                                        ))
                                                        .build(),
                                                CategoryCascade.builder()
                                                        .value("IPad")
                                                        .childList(List.of(
                                                                CategoryCascade.builder()
                                                                        .value("IPad air")
                                                                        .build(),
                                                                CategoryCascade.builder()
                                                                        .value("IPad pro")
                                                                        .build()
                                                        ))
                                                        .build(),
                                                CategoryCascade.builder()
                                                        .value("Watch")
                                                        .childList(List.of(
                                                                CategoryCascade.builder()
                                                                        .value("Apple Watch Series 11")
                                                                        .build(),
                                                                CategoryCascade.builder()
                                                                        .value("Apple Watch SE 3")
                                                                        .build(),
                                                                CategoryCascade.builder()
                                                                        .value("Apple Watch Ultra 3")
                                                                        .build()
                                                        ))
                                                        .build()
                                ))
                                .build())
                .build();
    }

    private CategoryDto getCategoryDto2() {
        return CategoryDto.builder()
                .region("Global")
                .category(
                        CategoryCascade.builder()
                                .value("Apple")
                                .key("Apple")
                                .childList(List.of(
                                        CategoryCascade.builder()
                                                .key("Mac")
                                                .value("Mac")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("Macbook air")
                                                                .value("Macbook air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Macbook pro")
                                                                .value("Macbook pro")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build(),
                                        CategoryCascade.builder()
                                                .key("IPhone")
                                                .value("IPhone")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("IPhone air")
                                                                .value("IPhone air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone pro")
                                                                .value("IPhone pro")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone 17")
                                                                .value("IPhone 17")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone 16")
                                                                .value("IPhone 16")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build(),
                                        CategoryCascade.builder()
                                                .key("IPad")
                                                .value("IPad")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("IPad air")
                                                                .value("IPad air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPad pro")
                                                                .value("IPad pro")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build(),
                                        CategoryCascade.builder()
                                                .key("Watch")
                                                .value("Watch")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("Apple Watch Series 11")
                                                                .value("Apple Watch Series 11")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Apple Watch SE 3")
                                                                .value("Apple Watch SE 3")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Apple Watch Ultra 3")
                                                                .value("Apple Watch Ultra 3")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .build())
                .build();
    }

    private CategoryDto getUpdateCategoryDtoMore() {
        return CategoryDto.builder()
                .region("DE")
                .category(
                        CategoryCascade.builder()
                                .value("Update Apple")
                                .key("Apple")
                                .childList(List.of(
                                        CategoryCascade.builder()
                                                .key("Mac")
                                                .value("Update Mac")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("Macbook air")
                                                                .value("Update Macbook air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Macbook pro")
                                                                .value("Update Macbook pro")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Macbook M4")
                                                                .value("New Macbook M4")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build(),
                                        CategoryCascade.builder()
                                                .key("IPhone")
                                                .value("Update IPhone")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("IPhone air")
                                                                .value("Update IPhone air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone pro")
                                                                .value("Update IPhone pro")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone 17")
                                                                .value("Update IPhone 17")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone 16")
                                                                .value("Update IPhone 16")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone 15")
                                                                .value("New IPhone 15")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build(),
                                        CategoryCascade.builder()
                                                .key("IPad")
                                                .value("Update IPad")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("IPad air")
                                                                .value("Update IPad air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPad pro")
                                                                .value("Update IPad pro")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build(),
                                        CategoryCascade.builder()
                                                .key("Watch")
                                                .value("Update Watch")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("Apple Watch Series 10")
                                                                .value("New Apple Watch Series 10")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Apple Watch Series 11")
                                                                .value("Update Apple Watch Series 11")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Apple Watch SE 3")
                                                                .value("Update Apple Watch SE 3")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Apple Watch Ultra 3")
                                                                .value("Update Apple Watch Ultra 3")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .build())
                .build();
    }

    private CategoryDto getUpdateCategoryDtoLess() {
        return CategoryDto.builder()
                .region("DE")
                .category(
                        CategoryCascade.builder()
                                .value("Update Apple")
                                .key("Apple")
                                .childList(List.of(
                                        CategoryCascade.builder()
                                                .key("Mac")
                                                .value("Update Mac")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("Macbook pro")
                                                                .value("Update Macbook pro")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build(),
                                        CategoryCascade.builder()
                                                .key("IPhone")
                                                .value("Update IPhone")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("IPhone air")
                                                                .value("Update IPhone air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone pro")
                                                                .value("Update IPhone pro")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone 17")
                                                                .value("Update IPhone 17")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build(),
                                        CategoryCascade.builder()
                                                .key("Watch")
                                                .value("Update Watch")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("Apple Watch SE 3")
                                                                .value("Update Apple Watch SE 3")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Apple Watch Ultra 3")
                                                                .value("Update Apple Watch Ultra 3")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .build())
                .build();
    }

    private CategoryDto getUpdateCategoryDtoReplace() {
        return CategoryDto.builder()
                .region("DE")
                .category(
                        CategoryCascade.builder()
                                .value("Update Apple")
                                .key("Apple")
                                .childList(List.of(
                                        CategoryCascade.builder()
                                                .key(null)
                                                .value("New Mac")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .value("New Macbook air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .value("New Macbook pro")
                                                                .childList(List.of())
                                                                .build()
                                                ))
                                                .build(),
                                        CategoryCascade.builder()
                                                .key("IPad")
                                                .value("Update IPad")
                                                .childList(List.of(
                                                        CategoryCascade.builder()
                                                                .key("IPad air")
                                                                .value("Update IPad air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPad pro")
                                                                .value("Update IPad pro")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone air")
                                                                .value("Update IPhone air")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone pro")
                                                                .value("Update IPhone pro")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone 17")
                                                                .value("Update IPhone 17")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("IPhone 16")
                                                                .value("Update IPhone 16")
                                                                .childList(List.of())
                                                                .build(),
                                                        CategoryCascade.builder()
                                                                .key("Watch")
                                                                .value("Update Watch")
                                                                .childList(List.of(
                                                                        CategoryCascade.builder()
                                                                                .key("Apple Watch Series 11")
                                                                                .value("Update Apple Watch Series 11")
                                                                                .childList(List.of())
                                                                                .build(),
                                                                        CategoryCascade.builder()
                                                                                .key("Apple Watch SE 3")
                                                                                .value("Update Apple Watch SE 3")
                                                                                .childList(List.of())
                                                                                .build(),
                                                                        CategoryCascade.builder()
                                                                                .key("Apple Watch Ultra 3")
                                                                                .value("Update Apple Watch Ultra 3")
                                                                                .childList(List.of())
                                                                                .build()
                                                                ))
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .build())
                .build();
    }
}
