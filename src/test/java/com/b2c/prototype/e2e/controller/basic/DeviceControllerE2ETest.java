//package com.b2c.prototype.e2e.controller.basic;
//
//import com.b2c.prototype.e2e.BasicE2ETest;
//import com.b2c.prototype.e2e.util.TestUtil;
//import com.b2c.prototype.modal.dto.payload.user.ResponseDeviceDto;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.sql.Connection;
//import java.sql.Statement;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class DeviceControllerE2ETest extends BasicE2ETest {
//
//    private static final String URL_TEMPLATE = "/api/v1/user/device";
//
//    @Test
//    void testActivateDevice() {
//        try {
//            mockMvc.perform(post(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestParams()))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(TestUtil.readFile("json/device/input/DeviceDto.json")))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testAddNewActivateDevice() {
//        try {
//            mockMvc.perform(post(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestParams()))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(TestUtil.readFile("json/device/input/NewDeviceDto.json")))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testDeleteDevice() {
//        try {
//            mockMvc.perform(delete(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestParams()))
//                            .content(TestUtil.readFile("json/device/input/DeviceDto.json"))
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetUserDevices() {
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestParams()))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            String jsonResponse = mvcResult.getResponse().getContentAsString();
//            List<ResponseDeviceDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/device/output/ResponseAllDevices.json");
//            List<ResponseDeviceDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            assertEquals(expectedList, actualList);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private Map<String, String> getRequestParams() {
//        return Map.of("userId", "123");
//    }
//
//}
