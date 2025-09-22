package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.payload.order.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserCreditCardDto;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserCreditCardControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/usercreditcard";

//    @BeforeEach
//    public void cleanUpDatabase() {
//        try (Connection connection = connectionHolder.getConnection()) {
//            connection.setAutoCommit(false);
//            Statement statement = connection.createStatement();
//            statement.execute("DELETE FROM user_credit_card");
//            statement.execute("DELETE FROM credit_card");
//
//            statement.execute("ALTER SEQUENCE user_credit_card_id_seq RESTART WITH 2");
//            statement.execute("ALTER SEQUENCE credit_card_id_seq RESTART WITH 2");
//            connection.commit();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to clean table: CreditCard", e);
//        }
//    }

    @Test
    void testPostUserCreditCard() {
        // loadDataSet("/datasets/e2e/user/user_credit_card/emptyE2ECreditCard.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestUserParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/credit_card/input/UserCreditCardDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // verifyExpectedData("/datasets/e2e/user/user_credit_card/testE2ECreditCard.yml",
                // new String[] {"id", "dateOfCreate"},
                // new String[] {"label", "value",
//        );
    }

    @Test
    void testPutUserCreditCard() {
        // loadDataSet("/datasets/e2e/user/user_credit_card/testE2ECreditCard.yml");
        Map<String, String> requestParams = Map.of("userId", "123", "cardNumber", "4444-1111-2222-3333");
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/credit_card/input/UpdateUserCreditCardDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // verifyExpectedData("/datasets/e2e/user/user_credit_card/updateE2ECreditCard.yml",
                // new String[] {"id", "dateOfCreate"},
                // new String[] {"label", "value",
//        );
    }

    @Test
    void testSetDefaultUserCreditCard() {
        // loadDataSet("/datasets/e2e/user/user_credit_card/testE2ECreditCard.yml");
        Map<String, String> requestParams = Map.of(
                "userId", "123",
                "cardNumber", "4444-1111-2222-3331"
        );

        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // verifyExpectedData("/datasets/e2e/user/user_credit_card/testSetDefaultE2ECreditCard.yml");
    }

    @Test
    void testDeleteUserCreditCard() {
        // loadDataSet("/datasets/e2e/user/user_credit_card/testE2ECreditCard.yml");
        Map<String, String> requestParams = Map.of(
                "userId", "123",
                "cardNumber", "4444-1111-2222-3331"
        );

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(requestParams)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // verifyExpectedData("/datasets/e2e/user/user_credit_card/emptyE2ECreditCard.yml");
    }

    @Test
    void testGetUserCreditCards() {
        // loadDataSet("/datasets/e2e/user/user_credit_card/testE2ECreditCard.yml");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestUserParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseUserCreditCardDto> actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/credit_card/output/ResponseAllUserCreditCards.json");
            List<ResponseUserCreditCardDto> expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllCreditCardsByCardNumber() {
        // loadDataSet("/datasets/e2e/user/user_credit_card/testE2ECreditCard.yml");
        Map<String, String> requestParams = Map.of("cardNumber", "4444-1111-2222-3333");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/all")
                            .params(getMultiValueMap(requestParams))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseCreditCardDto> actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/credit_card/output/ResponseAllCreditCards.json");
            List<ResponseCreditCardDto> expected = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetDefaultCreditCard() {
        // loadDataSet("/datasets/e2e/user/user_credit_card/testE2ECreditCard.yml");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/default")
                            .params(getMultiValueMap(getRequestUserParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ResponseUserCreditCardDto actual = objectMapper.readValue(jsonResponse, ResponseUserCreditCardDto.class);
            String expectedResultStr = TestUtil.readFile("json/credit_card/output/ResponseUserCreditCard.json");
            ResponseUserCreditCardDto expected = objectMapper.readValue(expectedResultStr, ResponseUserCreditCardDto.class);
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestUserParams() {
        return Map.of("userId", "123");
    }

}
