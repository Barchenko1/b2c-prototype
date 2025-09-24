package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.MessageTypeDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MessageTypeControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/user/message/type";

    @Test
    @DataSet(value = "datasets/e2e/user/message_type/emptyE2EMessageTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_type/testE2EMessageTypeDataSet.yml", orderBy = "id")
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
    @DataSet(value = "datasets/e2e/user/message_type/testE2EMessageTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_type/updateE2EMessageTypeDataSet.yml", orderBy = "id")
    public void testUpdateEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("InMail")
                .value("Update InMail")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "InMail")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_type/testE2EMessageTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_type/updateE2EMessageTypeDataSet.yml", orderBy = "id")
    public void testPatchEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("InMail")
                .value("Update InMail")
                .build();

        String jsonPayload = writeValueAsString(constantPayloadDto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "InMail")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_type/testE2EMessageTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_type/emptyE2EMessageTypeDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "InApp")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_type/testE2EMessageTypeDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<MessageTypeDto> constantPayloadDtoList = List.of(
                MessageTypeDto.builder()
                        .label("InMail")
                        .value("InMail")
                        .build(),
                MessageTypeDto.builder()
                        .label("InApp")
                        .value("InApp")
                        .build());

        List<MessageTypeDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<MessageTypeDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_type/testE2EMessageTypeDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        MessageTypeDto expected = MessageTypeDto.builder()
                .label("InApp")
                .value("InApp")
                .build();

        MessageTypeDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("value", "InApp")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(MessageTypeDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(expected);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .label("InApp")
                .value("InApp")
                .build();
    }
}
