package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.message.MessageTemplateDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MessageTemplateControllerE2ETest extends BasicE2ETest {
    private final String URL_TEMPLATE = "/api/v1/user/message/template";

    @Test
    @DataSet(value = "datasets/e2e/user/message_template/emptyE2EMessageTemplateDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", orderBy = "id", ignoreCols = {"id", "message_template_uniq_id"})
    public void testCreateEntity() {
        MessageTemplateDto dto = getMessageTemplateDto();
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
    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_template/updateE2EMessageTemplateDataSet.yml", orderBy = "id", ignoreCols = {"id", "message_template_uniq_id"})
    public void testUpdateEntity() {
        MessageTemplateDto dto = MessageTemplateDto.builder()
                .title("title3")
                .message("message3")
                .build();

        String jsonPayload = writeValueAsString(dto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("messageTemplateId", "123")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_template/updateE2EMessageTemplateDataSet.yml", orderBy = "id", ignoreCols = {"message_template_uniq_id"})
    public void testPatchEntity() {
        MessageTemplateDto dto = MessageTemplateDto.builder()
                .title("title3")
                .message("message3")
                .build();

        String jsonPayload = writeValueAsString(dto);
        webTestClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("messageTemplateId", "123")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/message_template/emptyE2EMessageTemplateDataSet.yml", orderBy = "id")
    public void testDeleteEntity() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("messageTemplateId", "125")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
    public void testGetEntities() {
        List<MessageTemplateDto> constantPayloadDtoList = List.of(
                MessageTemplateDto.builder()
                        .title("title1")
                        .message("message1")
                        .messageTemplateId("123")
                        .build(),
                MessageTemplateDto.builder()
                        .title("title2")
                        .message("message2")
                        .messageTemplateId("125")
                        .build());

        List<MessageTemplateDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<MessageTemplateDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual).isEqualTo(constantPayloadDtoList);
    }

    @Test
    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
    public void testGetEntity() {
        MessageTemplateDto expected = MessageTemplateDto.builder()
                .title("title1")
                .message("message1")
                .messageTemplateId("123")
                .build();

        MessageTemplateDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("messageTemplateId", "123")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(MessageTemplateDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    private MessageTemplateDto getMessageTemplateDto() {
        return MessageTemplateDto.builder()
                .title("title2")
                .message("message2")
                .build();
    }
}
