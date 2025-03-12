package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.response.ResponseUserDetailsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserDetailsControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/user";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM user_address");
            statement.execute("DELETE FROM address");
            statement.execute("DELETE FROM user_credit_card");
            statement.execute("DELETE FROM credit_card");
            statement.execute("DELETE FROM device");
            statement.execute("DELETE FROM user_details");
            statement.execute("DELETE FROM contact_info");
            statement.execute("DELETE FROM contact_phone");

            statement.execute("ALTER SEQUENCE contact_info_id_seq RESTART WITH 1");
            statement.execute("ALTER SEQUENCE user_address_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE address_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE user_credit_card_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE credit_card_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE contact_phone_id_seq RESTART WITH 2");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: user_details", e);
        }
    }

    @Test
    void testCreateUserDetails() {
        loadDataSet("/datasets/user/user_details/emptyE2EUserDetails.yml");
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/user_details/input/RegistrationUserDetailsDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/user_details/saveE2ERegistrationUserDetails.yml",
                new String[] {"id", "dateOfCreate", "user_id"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testCreateFullUserDetails() {
        loadDataSet("/datasets/user/user_details/emptyE2EUserDetails.yml");
        try {
            mockMvc.perform(post(URL_TEMPLATE + "/full")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/user_details/input/UserDetailsDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/user_details/saveE2EUserDetails.yml",
                new String[] {"id", "dateOfCreate", "user_id"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testPutUserDetails() {
        loadDataSet("/datasets/user/user_details/testE2EUserDetails.yml");

        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/user_details/input/UpdateUserDetailsDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/user_details/updateE2EUserDetails.yml",
                new String[] {"id", "dateOfCreate", "user_id"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testPatchUserDetails() {
        loadDataSet("/datasets/user/user_details/testE2EUserDetails.yml");

        try {
            mockMvc.perform(patch(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/user_details/input/UpdateUserDetailsDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/user_details/updateE2EUserDetails.yml",
                new String[] {"id", "dateOfCreate", "user_id"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testPatchUserDetailsStatus() {
        loadDataSet("/datasets/user/user_details/testE2EUserDetails.yml");
        MultiValueMap<String, String> multiValueMap =  new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "123");
        multiValueMap.add("status", String.valueOf(true));
        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/status")
                            .params(multiValueMap)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/user_details/updateStatusE2EUserDetails.yml",
                new String[] {"id", "dateOfCreate", "user_id"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testPatchUserDetailsVerifyEmail() {
        loadDataSet("/datasets/user/user_details/testE2EUserDetails.yml");
        MultiValueMap<String, String> multiValueMap =  new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "123");
        multiValueMap.add("verifyEmail", String.valueOf(true));
        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/verifyEmail")
                            .params(multiValueMap)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/user_details/updateVerifyEmailE2EUserDetails.yml",
                new String[] {"id", "dateOfCreate", "user_id"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testPatchUserDetailsVerifyPhone() {
        loadDataSet("/datasets/user/user_details/testE2EUserDetails.yml");
        MultiValueMap<String, String> multiValueMap =  new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "123");
        multiValueMap.add("verifyPhone", String.valueOf(true));
        try {
            mockMvc.perform(patch(URL_TEMPLATE + "/verifyPhone")
                            .params(multiValueMap)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/user_details/updateVerifyPhoneE2EUserDetails.yml",
                new String[] {"id", "dateOfCreate", "user_id"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testDeleteUserDetails() {
        loadDataSet("/datasets/user/user_details/testE2EUserDetails.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                    .params(getMultiValueMap(getRequestParams())))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/user_details/emptyE2EUserDetails.yml");
    }

    @Test
    void testGetUserDetails() {
        loadDataSet("/datasets/user/user_details/testAllE2EUserDetails.yml");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ResponseUserDetailsDto actual = objectMapper.readValue(jsonResponse, ResponseUserDetailsDto.class);
            String expectedResultStr = TestUtil.readFile("json/user_details/output/ResponseUserDetails.json");
            ResponseUserDetailsDto expected = objectMapper.readValue(expectedResultStr, ResponseUserDetailsDto.class);
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllUserDetails() {
        loadDataSet("/datasets/user/user_details/testAllE2EUserDetails.yml");

        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE + "/all")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<ResponseUserDetailsDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/user_details/output/ResponseAllUserDetails.json");
            List<ResponseUserDetailsDto> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestParams() {
        return Map.of("userId", "123");
    }

}
