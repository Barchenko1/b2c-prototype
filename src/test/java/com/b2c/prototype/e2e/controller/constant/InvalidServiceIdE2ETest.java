//package com.b2c.prototype.e2e.controller.constant;
//
//import com.b2c.prototype.e2e.BasicE2ETest;
//import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class InvalidServiceIdE2ETest extends BasicE2ETest {
//
//    @Test
//    void testPostConstantEntityInvalidServiceId() {
//        try {
//            String jsonPayload = objectMapper.writeValueAsString(getPayloadDto());
//            mockMvc.perform(post("/api/v1/constant")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .header("serviceId", "invalidServiceId")
//                            .content(jsonPayload))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content().string("Invalid serviceId."));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testPutConstantEntityInvalidServiceId() {
//        try {
//            String jsonPayload = objectMapper.writeValueAsString(getPayloadDto());
//            mockMvc.perform(put("/api/v1/constant")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .header("serviceId", "invalidServiceId")
//                            .param("value", "invalidValue")
//                            .content(jsonPayload))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content().string("Invalid serviceId."));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testPatchConstantEntityInvalidServiceId() {
//        try {
//            String jsonPayload = objectMapper.writeValueAsString(getPayloadDto());
//            mockMvc.perform(patch("/api/v1/constant")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .header("serviceId", "invalidServiceId")
//                            .param("value", "invalidValue")
//                            .content(jsonPayload))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content().string("Invalid serviceId."));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testDeleteConstantEntityInvalidServiceId() {
//        try {
//            mockMvc.perform(delete("/api/v1/singlevalue")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .header("serviceId", "invalidServiceId")
//                            .param("value", "invalidValue"))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content().string("Invalid serviceId."));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetConstantEntityInvalidServiceId() {
//        try {
//            mockMvc.perform(get("/api/v1/constant")
//                            .header("Accept-Language", "en")
//                            .header("serviceId", "invalidServiceId")
//                            .param("value", "invalidValue"))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(content().string("Invalid serviceId."));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetAllConstantEntitiesInvalidServiceId() throws Exception {
//        mockMvc.perform(get("/api/v1/constant/all")
//                        .header("Accept-Language", "en")
//                        .header("serviceId", "invalidServiceId")
//                        .param("value", "invalidValue"))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Invalid serviceId."));
//    }
//
//    ConstantPayloadDto getPayloadDto() {
//       return ConstantPayloadDto.builder()
//                .label("Apple")
//                .value("Apple")
//                .build();
//    }
//}
