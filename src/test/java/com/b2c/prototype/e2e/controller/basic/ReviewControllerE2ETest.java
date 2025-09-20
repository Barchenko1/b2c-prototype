package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;

import com.b2c.prototype.modal.dto.payload.review.ResponseReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerE2ETest extends BasicE2ETest {
    private static final String URL_TEMPLATE = "/api/v1/review";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM review_comment");
            statement.execute("DELETE FROM review");

            statement.execute("ALTER SEQUENCE review_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE review_comment_id_seq RESTART WITH 2");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: review", e);
        }
    }

    @Test
    void testPostReview() {
        loadDataSet("/datasets/e2e/review/review/emptyE2EReview.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestArticularUserParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/review/input/ReviewDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/review/review/testE2EReview.yml",
                new String[] {"id", "dateOfCreate", "reviewId"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testPutReview() {
        loadDataSet("/datasets/e2e/review/review/testE2EReview.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestArticularUserReviewParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/review/input/UpdateReviewDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/review/review/updateE2EReview.yml",
                new String[] {"id", "dateOfCreate", "reviewId"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testChangeReviewStatus() {
        loadDataSet("/datasets/e2e/review/review/testE2EReview.yml");
        Map<String, String> requestParams = Map.of("articularId", "1", "reviewId", "review123", "status", "DONE");
        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/review/review/testE2EUpdateStatusReview.yml",
                new String[] {"id", "dateOfCreate", "reviewId"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testPutCommentOnReview() {
        loadDataSet("/datasets/e2e/review/review/testE2EReview.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE + "/comment")
                            .params(getMultiValueMap(getRequestArticularUserReviewParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/review/input/CommentDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/review/review/testE2EReviewComment.yml",
                new String[] {"id", "dateOfCreate", "reviewId", "commentId"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testPutComment2OnReview() {
        loadDataSet("/datasets/e2e/review/review/testE2EReviewComment.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE + "/comment")
                            .params(getMultiValueMap(getRequestArticularUserReviewParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/review/input/CommentDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/review/review/testE2EReviewComment2.yml",
                new String[] {"id", "dateOfCreate", "reviewId", "commentId"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testDeleteReviewWithUser() {
        loadDataSet("/datasets/e2e/review/review/testE2EReviewComment2.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestArticularUserReviewParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/review/review/emptyE2EReview.yml",
                new String[] {"id", "dateOfCreate", "reviewId"},
                new String[] {"label", "value", "OPTION_ITEM_ID"}
        );
    }

    @Test
    void testDeleteReview() {
        loadDataSet("/datasets/e2e/review/review/testE2EReviewComment2.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestArticularReviewParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/review/review/emptyE2EReview.yml",
                new String[] {"id", "dateOfCreate", "reviewId"},
                new String[] {"label", "value", "OPTION_ITEM_ID"}
        );
    }

    @Test
    void testUpdateReviewComment() {
        loadDataSet("/datasets/e2e/review/review/testE2EReviewComment.yml");
        Map<String, String> requestParams = Map.of(
                "articularId", "1",
                "userId", "123",
                "reviewId", "review123",
                "commentId", "comment123");
        try {
            mockMvc.perform(post(URL_TEMPLATE + "/comment")
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/review/input/UpdateCommentDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/review/review/testE2EReviewUpdateComment.yml",
                new String[] {"id", "dateOfCreate", "reviewId"},
                new String[] {"label", "value", "OPTION_ITEM_ID"}
        );
    }

    @Test
    void testDeleteReviewComment() {
        loadDataSet("/datasets/e2e/review/review/testE2EReviewComment2.yml");
        Map<String, String> requestParams = Map.of("articularId", "1", "userId", "123", "reviewId", "review123", "commentId", "comment121");
        try {
            mockMvc.perform(delete(URL_TEMPLATE + "/comment")
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/e2e/review/review/testE2EReviewComment.yml",
                new String[] {"id", "dateOfCreate", "reviewId"},
                new String[] {"label", "value", "OPTION_ITEM_ID"}
        );
    }

    @Test
    void testGetReviewListByUserId() {
        loadDataSet("/datasets/e2e/review/review/testE2EReviewComment2.yml");
        Map<String, String> map = Map.of("userId", "123");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/list")
                            .params(getMultiValueMap(map))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseReviewDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/review/output/ResponseReviewDtoList.json");
            List<ResponseReviewDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            actualList.sort(Comparator.comparing(ResponseReviewDto::getTitle));
            expectedList.sort(Comparator.comparing(ResponseReviewDto::getTitle));
            actualList.forEach(e -> e.getComments()
                    .sort(Comparator.comparing(ResponseReviewCommentDto::getCommentId)));
            expectedList.forEach(e -> e.getComments()
                    .sort(Comparator.comparing(ResponseReviewCommentDto::getCommentId)));
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetReviewListByArticularId() {
        loadDataSet("/datasets/e2e/review/review/testE2EReviewComment2.yml");
        Map<String, String> map = Map.of("articularId", "1");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/list")
                            .params(getMultiValueMap(map))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseReviewDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/review/output/ResponseReviewDtoList.json");
            List<ResponseReviewDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            actualList.sort(Comparator.comparing(ResponseReviewDto::getTitle));
            expectedList.sort(Comparator.comparing(ResponseReviewDto::getTitle));
            actualList.forEach(e -> e.getComments()
                    .sort(Comparator.comparing(ResponseReviewCommentDto::getCommentId)));
            expectedList.forEach(e -> e.getComments()
                    .sort(Comparator.comparing(ResponseReviewCommentDto::getCommentId)));
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetReviewByArticularIdAndReviewId() {
        loadDataSet("/datasets/e2e/review/review/testE2EReviewComment.yml");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestArticularUserReviewParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ResponseReviewDto actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/review/output/ResponseReviewDto.json");
            ResponseReviewDto expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetReviewCommentListByArticularIdAndReviewId() {
        loadDataSet("/datasets/e2e/review/review/testE2EReviewComment2.yml");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/comments")
                            .params(getMultiValueMap(getRequestArticularUserReviewParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseReviewCommentDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/review/output/ResponseReviewCommentDtoList.json");
            List<ResponseReviewCommentDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            actualList.sort(Comparator.comparing(ResponseReviewCommentDto::getCommentId));
            expectedList.sort(Comparator.comparing(ResponseReviewCommentDto::getCommentId));
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestArticularUserParams() {
        return Map.of("articularId", "1", "userId", "123");
    }
    private Map<String, String> getRequestArticularReviewParams() {
        return Map.of("articularId", "1", "reviewId", "review123");
    }
    private Map<String, String> getRequestArticularUserReviewParams() {
        return Map.of("articularId", "1", "userId", "123", "reviewId", "review123");
    }
}
