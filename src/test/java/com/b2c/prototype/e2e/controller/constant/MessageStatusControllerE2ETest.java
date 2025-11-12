package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.MessageStatusDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MessageStatusControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/user/message/status";

    @Test
    @DataSet(value = "datasets/e2e/user/message_status/emptyE2EMessageStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_status/testE2EMessageStatusDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/user/message_status/testE2EMessageStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_status/updateE2EMessageStatusDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update New")
                .key("Update New")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "New")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_status/testE2EMessageStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_status/updateE2EMessageStatusDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .value("Update New")
                .key("Update New")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "New")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_status/testE2EMessageStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_status/emptyE2EMessageStatusDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Read")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_status/testE2EMessageStatusDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<MessageStatusDto> constantPayloadDtoList = List.of(
                MessageStatusDto.builder()
                        .value("New")
                        .key("New")
                        .build(),
                MessageStatusDto.builder()
                        .value("Read")
                        .key("Read")
                        .build());

        List<MessageStatusDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<MessageStatusDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_status/testE2EMessageStatusDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        MessageStatusDto expected = MessageStatusDto.builder()
                .value("Read")
                .key("Read")
                .build();

        MessageStatusDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("key", "Read")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(MessageStatusDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .value("Read")
                .key("Read")
                .build();
    }

}
