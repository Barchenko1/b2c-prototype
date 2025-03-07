package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContactInfoControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/contactInfo";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM user_details");
            statement.execute("DELETE FROM contact_info");
            statement.execute("DELETE FROM contact_phone");

            statement.execute("ALTER SEQUENCE contact_info_id_seq RESTART WITH 1");
            statement.execute("ALTER SEQUENCE contact_phone_id_seq RESTART WITH 2");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: contact_info", e);
        }
    }

    @Test
    void testPostContactInfo() {
        loadDataSet("/datasets/user/contact_info/emptyE2EContactInfoDataSet.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/contact_info/input/ContactInfoDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/contact_info/saveE2EContactInfoDataSet.yml",
                new String[] {"id", "dateOfCreate"},
                new String[] {"label", "value"}
        );
    }

    @Test
    void testPutContactInfo() {
        loadDataSet("/datasets/user/contact_info/testE2EContactInfoDataSet.yml");

        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/contact_info/input/UpdateContactInfoDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/contact_info/updateE2EContactInfoDataSet.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testDeleteContactInfo() {
        loadDataSet("/datasets/user/contact_info/testE2EContactInfoDataSet.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                    .params(getMultiValueMap(getRequestParams())))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/user/contact_info/emptyE2EContactInfoDataSet.yml");
    }

    @Test
    void testGetContactInfo() {
        loadDataSet("/datasets/user/contact_info/testE2EContactInfoDataSet.yml");
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
            ContactInfoDto actual = objectMapper.readValue(jsonResponse, ContactInfoDto.class);
            String expectedResultStr = TestUtil.readFile("json/contact_info/output/ResponseContactInfo.json");
            ContactInfoDto expected = objectMapper.readValue(expectedResultStr, ContactInfoDto.class);
            assertEquals(expected, actual);
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
