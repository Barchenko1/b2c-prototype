//package com.b2c.prototype.e2e.controller.constant;
//
//import com.b2c.prototype.e2e.BasicE2ETest;
//import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
//import com.b2c.prototype.modal.entity.message.MessageStatus;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.github.database.rider.core.api.dataset.DataSet;
//import com.github.database.rider.core.api.dataset.ExpectedDataSet;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class MessageTemplateControllerE2ETest extends BasicE2ETest {
//    private final String URL_TEMPLATE = "/api/v1/user/message/template";
//
//    @Test
//    @DataSet(value = "datasets/e2e/user/message_template/emptyE2EMessageTemplateDataSet.yml", cleanBefore = true)
//    @ExpectedDataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", orderBy = "id")
//    public void testCreateEntity() {
//        ConstantPayloadDto dto = getConstantPayloadDto();
//        String jsonPayload = writeValueAsString(dto);
//
//        mockMvc.post()
//                .uri(URL_TEMPLATE)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .bodyValue(jsonPayload)
//                .exchange()
//                .expectStatus().isOk();
//    }
//
//    @Test
//    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
//    @ExpectedDataSet(value = "datasets/e2e/user/message_template/updateE2EMessageTemplateDataSet.yml", orderBy = "id")
//    public void testUpdateEntity() {
//        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
//                .label("New")
//                .value("Update New")
//                .build();
//        try {
//            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);
//
//            mockMvc.perform(put(URL_TEMPLATE)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .param("value", "New")
//                            .content(jsonPayload))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
//    @ExpectedDataSet(value = "datasets/e2e/user/message_template/updateE2EMessageTemplateDataSet.yml", orderBy = "id")
//    public void testPatchEntity() {
//        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
//                .label("New")
//                .value("Update New")
//                .build();
//
//        try {
//            String jsonPayload = objectMapper.writeValueAsString(constantPayloadDto);
//
//            mockMvc.perform(put(URL_TEMPLATE)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .param("value", "New")
//                            .content(jsonPayload))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
//    @ExpectedDataSet(value = "datasets/e2e/user/message_template/emptyE2EMessageTemplateDataSet.yml", orderBy = "id")
//    public void testDeleteEntity() {
//        try {
//            mockMvc.perform(delete(URL_TEMPLATE)
//                            .param("value", "Read"))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
//    public void testGetEntities() {
//        List<MessageStatus> constantPayloadDtoList = List.of(
//                MessageStatus.builder()
//                        .label("New")
//                        .value("New")
//                        .build(),
//                MessageStatus.builder()
//                        .label("Read")
//                        .value("Read")
//                        .build());
//        try {
//
//            MvcResult mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/all"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                    .andExpect(status().is2xxSuccessful())
//                    .andReturn();
//
//            // assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    @DataSet(value = "datasets/e2e/user/message_template/testE2EMessageTemplateDataSet.yml", cleanBefore = true)
//    public void testGetEntity() {
//        ConstantPayloadDto dto = getConstantPayloadDto();
//        try {
//
//            MvcResult mvcResult = mockMvc.perform(get(URL_TEMPLATE)
//                            .param("value", "Read"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                    .andExpect(status().is2xxSuccessful())
//                    .andReturn();
//
//            // assertMvcResult(mvcResult, dto);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private ConstantPayloadDto getConstantPayloadDto() {
//        return ConstantPayloadDto.builder()
//                .label("Read")
//                .value("Read")
//                .build();
//    }
//}
