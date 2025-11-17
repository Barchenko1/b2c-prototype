//package com.b2c.prototype.e2e.controller.constant;
//
//import com.b2c.prototype.e2e.BasicE2ETest;
//import com.b2c.prototype.e2e.util.TestUtil;
//import com.b2c.prototype.modal.modal.payload.post.ResponsePostDto;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class PostControllerE2ETest extends BasicE2ETest {
//    private static final String URL_TEMPLATE = "/api/v1/post";
//
////    @BeforeEach
////    public void cleanUpDatabase() {
////        try (Connection connection = connectionHolder.getConnection()) {
////            connection.setAutoCommit(false);
////            Statement statement = connection.createStatement();
////            statement.execute("DELETE FROM post");
////
////            statement.execute("ALTER SEQUENCE post_id_seq RESTART WITH 2");
////            connection.commit();
////        } catch (Exception e) {
////            throw new RuntimeException("Failed to clean table: messages", e);
////        }
////    }
//
//    @Test
//    void testPostPost() {
//        // loadDataSet("/datasets/e2e/post/emptyE2EPost.yml");
//
//        try {
//            mockMvc.perform(post(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestArticularParams()))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(TestUtil.readFile("json/post/input/PostDto.json")))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // verifyExpectedData("/datasets/e2e/post/testE2EPost.yml",
//                // new String[] {"id", "dateOfCreate", "postId"},
//                // new String[] {"label", "value",
////        );
//    }
//
//    @Test
//    void testPutPostOnPost() {
//        // loadDataSet("/datasets/e2e/post/testE2EPost.yml");
//        try {
//            mockMvc.perform(post(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestArticularParams()))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(TestUtil.readFile("json/post/input/PostWithParentPostDto.json")))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // verifyExpectedData("/datasets/e2e/post/testAddE2EPost.yml",
//                // new String[] {"id", "dateOfCreate", "postId"},
//                // new String[] {"label", "value",
////        );
//    }
//
//    @Test
//    void testPutPost() {
//        // loadDataSet("/datasets/e2e/post/testE2EPost.yml");
//
//        try {
//            mockMvc.perform(post(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestArticularPostParams()))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(TestUtil.readFile("json/post/input/UpdatePostDto.json")))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // verifyExpectedData("/datasets/e2e/post/updateE2EPost.yml",
//                // new String[] {"id", "dateOfCreate", "postId"},
//                // new String[] {"label", "value",
////        );
//    }
//
//    @Test
//    void testDeletePost() {
//        // loadDataSet("/datasets/e2e/post/testE2EPost.yml");
//
//        try {
//            mockMvc.perform(delete(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestArticularPostParams()))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // verifyExpectedData("/datasets/e2e/post/emptyE2EPost.yml",
//                // new String[] {"id", "dateOfCreate", "postId"},
//                // new String[] {"label", "value", "OPTION_ITEM_ID"}
////        );
//    }
//
//    @Test
//    void testGetPostListByUserId() {
//        // loadDataSet("/datasets/e2e/post/testWithUserDetailsE2EPost.yml");
//
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/list")
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
//            List<ResponsePostDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/post/output/ResponsePostDtoListByUserId.json");
//            List<ResponsePostDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            actualList.sort(Comparator.comparing(ResponsePostDto::getAuthorEmail));
//            expectedList.sort(Comparator.comparing(ResponsePostDto::getAuthorEmail));
//            assertEquals(expectedList, actualList);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetPostListByArticularId() {
//        // loadDataSet("/datasets/e2e/post/testAddE2EPost.yml");
//
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/list")
//                            .params(getMultiValueMap(getRequestArticularParams()))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            String jsonResponse = mvcResult.getResponse().getContentAsString();
//            List<ResponsePostDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/post/output/ResponsePostDtoListByArticularId.json");
//            List<ResponsePostDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            actualList.sort(Comparator.comparing(ResponsePostDto::getAuthorEmail));
//            expectedList.sort(Comparator.comparing(ResponsePostDto::getAuthorEmail));
//            assertEquals(expectedList, actualList);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetPostListByEmail() {
//        // loadDataSet("/datasets/e2e/post/testAddE2EPost.yml");
//
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/list")
//                            .params(getMultiValueMap(getRequestEmailParams()))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            String jsonResponse = mvcResult.getResponse().getContentAsString();
//            List<ResponsePostDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/post/output/ResponsePostDtoListByEmail.json");
//            List<ResponsePostDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
//            actualList.sort(Comparator.comparing(ResponsePostDto::getPostId));
//            expectedList.sort(Comparator.comparing(ResponsePostDto::getPostId));
//            assertEquals(expectedList, actualList);
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException("Error processing the JSON response", e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void testGetPostByArticularIdPostId() {
//        // loadDataSet("/datasets/e2e/post/testAddE2EPost.yml");
//
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
//                            .params(getMultiValueMap(getRequestArticularPostParams()))
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andReturn();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            String jsonResponse = mvcResult.getResponse().getContentAsString();
//            ResponsePostDto actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
//            String expectedResultStr = TestUtil.readFile("json/post/output/ResponsePostDto.json");
//            ResponsePostDto expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
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
//    private Map<String, String> getRequestArticularParams() {
//        return Map.of("articularId", "1");
//    }
//    private Map<String, String> getRequestArticularPostParams() {
//        return Map.of("articularId", "1", "postId", "post121");
//    }
//    private Map<String, String> getRequestEmailParams() {
//        return Map.of("email", "janedoe@example.com");
//    }
//
//}
