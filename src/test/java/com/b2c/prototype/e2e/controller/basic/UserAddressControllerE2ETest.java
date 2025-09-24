//package com.b2c.prototype.e2e.controller.basic;
//
//import com.b2c.prototype.e2e.BasicE2ETest;
//import com.b2c.prototype.e2e.util.TestUtil;
//import com.b2c.prototype.modal.dto.payload.order.AddressDto;
//import com.b2c.prototype.modal.dto.payload.user.ResponseUserAddressDto;
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
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class UserAddressControllerE2ETest extends BasicE2ETest {
//
//    private static final String URL_TEMPLATE = "/api/v1/address";
//
////    @BeforeEach
////    public void cleanUpDatabase() {
////        try (Connection connection = connectionHolder.getConnection()) {
////            connection.setAutoCommit(false);
////            Statement statement = connection.createStatement();
////            statement.execute("DELETE FROM user_address");
////            statement.execute("DELETE FROM address");
////
////            statement.execute("ALTER SEQUENCE user_address_id_seq RESTART WITH 2");
////            statement.execute("ALTER SEQUENCE address_id_seq RESTART WITH 2");
////            connection.commit();
////        } catch (Exception e) {
////            throw new RuntimeException("Failed to clean table: address", e);
////        }
////    }
//
//    @Test
//    void testPostUserAddress() {
//        // loadDataSet("/datasets/e2e/user/user_address/emptyE2EAddress.yml");
//
//        try {
//            mockMvc.perform(post(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestUserParams()))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(TestUtil.readFile("json/address/input/UserAddressDto.json")))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // verifyExpectedData("/datasets/e2e/user/user_address/testE2EAddress.yml",
//                // new String[] {"id", "dateOfCreate"},
//                // new String[] {"label", "value",
////        );
//    }
//
//    @Test
//    void testPutUserAddress() {
//        // loadDataSet("/datasets/e2e/user/user_address/testE2EAddress.yml");
//        Map<String, String> requestParams = Map.of("userId","123","addressId","USA/New York/5th Avenue/10/202");
//        try {
//            mockMvc.perform(post(URL_TEMPLATE)
//                            .params(getMultiValueMap(requestParams))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(TestUtil.readFile("json/address/input/UpdateUserAddressDto.json")))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // verifyExpectedData("/datasets/e2e/user/user_address/updateE2EAddress.yml",
//                // new String[] {"id", "dateOfCreate"},
//                // new String[] {"label", "value",
////        );
//    }
//
//    @Test
//    void testSetDefaultUserAddress() {
//        // loadDataSet("/datasets/e2e/user/user_address/testE2EAddress.yml");
//        Map<String, String> requestParams = Map.of("userId","123","addressId","comb");
//        try {
//            mockMvc.perform(put(URL_TEMPLATE)
//                            .params(getMultiValueMap(requestParams)))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // verifyExpectedData("/datasets/e2e/user/user_address/testSetDefaultE2EAddress.yml",
//                // new String[] {"id", "dateOfCreate"},
//                // new String[] {"label", "value",
////        );
//    }
//
//    @Test
//    void testDeleteUserAddress() {
//        // loadDataSet("/datasets/e2e/user/user_address/testE2EAddress.yml");
//        Map<String, String> requestParams = Map.of("userId","123","addressId","USA/New York/5th Avenue/10/202");
//
//        try {
//            mockMvc.perform(delete(URL_TEMPLATE)
//                            .params(getMultiValueMap(requestParams)))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // verifyExpectedData("/datasets/e2e/user/user_address/deleteE2EAddress.yml");
//    }
//
//    @Test
//    void testGetAllAddressesByAddress() {
//        // loadDataSet("/datasets/e2e/user/user_address/testE2EAddress.yml");
//        Map<String, String> requestParams = Map.of("userId","123","addressId","USA/New York/5th Avenue/10/202");
//
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/all")
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
//            List<AddressDto> actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/address/output/ResponseAllAddresses.json");
//            List<AddressDto> expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            assertEquals(expected, actual);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetUserAddressListByUserId() {
//        // loadDataSet("/datasets/e2e/user/user_address/testE2EAddress.yml");
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
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
//            List<ResponseUserAddressDto> actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/address/output/ResponseAllUserAddresses.json");
//            List<ResponseUserAddressDto> expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            assertEquals(expected, actual);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetDefaultUserAddress() {
//        // loadDataSet("/datasets/e2e/user/user_address/testE2EAddress.yml");
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/default")
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
//            ResponseUserAddressDto actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/address/output/ResponseUserAddress.json");
//            ResponseUserAddressDto expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            assertEquals(expected, actual);
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
//
//}
