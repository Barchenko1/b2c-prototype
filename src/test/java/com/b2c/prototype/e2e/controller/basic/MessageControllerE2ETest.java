//package com.b2c.prototype.e2e.controller.basic;
//
//import com.b2c.prototype.e2e.BasicE2ETest;
//import com.b2c.prototype.e2e.util.TestUtil;
//import com.b2c.prototype.modal.modal.payload.message.ResponseMessageOverviewDto;
//import com.b2c.prototype.modal.modal.payload.message.ResponseMessagePayloadDto;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.sql.Connection;
//import java.sql.Statement;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class MessageControllerE2ETest extends BasicE2ETest {
//    private static final String URL_TEMPLATE = "/api/v1/user/message";
//
//    @Test
//    void testPostMessage() {
//        try {
//            mockMvc.post()
//                    .uri(uriBuilder -> uriBuilder
//                            .path(URL_TEMPLATE)
//                            .queryParams(toParams(getRequestUserParams()))
//                            .build())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .bodyValue(TestUtil.readFile("json/message/input/MessageTemplateDto.json"))
//                    .exchange()
//                    .expectStatus().isOk();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testPutMessage() {
//        try {
////            mockMvc.perform(put(URL_TEMPLATE)
////                            .params(getMultiValueMap(getRequestUserMessageParams()))
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(TestUtil.readFile("json/message/input/UpdateMessageDto.json")))
////                    .andExpect(status().isOk());
//            mockMvc.put()
//                    .uri(uriBuilder -> uriBuilder
//                            .path(URL_TEMPLATE)
//                            .queryParams(toParams(getRequestUserParams()))
//                            .build())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .bodyValue(TestUtil.readFile("json/message/input/UpdateMessageDto.json"))
//                    .exchange()
//                    .expectStatus().isOk();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testChangeMessageStatus() {
//        Map<String, String> requestParams = Map.of("userId", "123", "messageId", "123", "status", "read");
//        try {
//            mockMvc.perform(put(URL_TEMPLATE + "/status")
//                            .params(getMultiValueMap(requestParams))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(TestUtil.readFile("json/message/input/UpdateMessageDto.json")))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testDeleteByMessageId() {
//        Map<String, String> requestParams = Map.of("messageId", "123");
//        try {
//            mockMvc.perform(delete(URL_TEMPLATE)
//                            .params(getMultiValueMap(requestParams))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testDeleteByUserIdAndMessageId() {
//        try {
//            mockMvc.perform(delete(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestUserMessageParams()))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testCleanUpMessage() {
//        try {
//            mockMvc.perform(delete(URL_TEMPLATE + "/clean")
//                            .params(getMultiValueMap(getRequestUserMessageParams()))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetMessageOverviewBySenderEmail() {
//        Map<String, String> requestParams = Map.of("sender", "sender.test@example.com");
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/sender")
//                            .params(getMultiValueMap(requestParams))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            String jsonResponse = mvcResult.getResponse().getContentAsString();
//            List<ResponseMessageOverviewDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/message/output/ResponseSenderMessageOverviewDtoList.json");
//            List<ResponseMessageOverviewDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            assertEquals(expectedList, actualList);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetMessageOverviewByReceiverEmail() {
//        Map<String, String> requestParams = Map.of("receiver", "jone.doe@example.com");
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/receiver")
//                            .params(getMultiValueMap(requestParams))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            String jsonResponse = mvcResult.getResponse().getContentAsString();
//            List<ResponseMessageOverviewDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/message/output/ResponseReceiverMessageOverviewDtoList.json");
//            List<ResponseMessageOverviewDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            assertEquals(expectedList, actualList);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetMessageOverviewListByUserId() {
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/overview")
//                            .params(getMultiValueMap(getRequestUserParams()))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            String jsonResponse = mvcResult.getResponse().getContentAsString();
//            List<ResponseMessageOverviewDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/message/output/ResponseMessageOverviewDtoList.json");
//            List<ResponseMessageOverviewDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            actualList.sort(Comparator.comparing(ResponseMessageOverviewDto::getSender));
//            expectedList.sort(Comparator.comparing(ResponseMessageOverviewDto::getSender));
//            assertEquals(expectedList, actualList);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetMessagePayloadDto() {
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/payload")
//                            .params(getMultiValueMap(getRequestUserMessageParams()))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            String jsonResponse = mvcResult.getResponse().getContentAsString();
//            ResponseMessagePayloadDto actualList = objectMapper.readValue(jsonResponse, ResponseMessagePayloadDto.class);
//            String expectedResultStr = TestUtil.readFile("json/message/output/ResponseMessagePayloadDto.json");
//            ResponseMessagePayloadDto expectedList = objectMapper.readValue(expectedResultStr, ResponseMessagePayloadDto.class);
//            assertEquals(expectedList, actualList);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private Map<String, String> getRequestUserParams() {
//        return Map.of("userId", "123");
//    }
//    private Map<String, String> getRequestUserMessageParams() {
//        return Map.of("userId", "123", "messageId", "123");
//    }
//
//    private MultiValueMap<String, String> toParams(Map<String, String> map) {
//        var m = new LinkedMultiValueMap<String, String>();
//        map.forEach(m::add);
//        return m;
//    }
//}
