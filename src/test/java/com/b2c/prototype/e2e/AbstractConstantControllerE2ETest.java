package com.b2c.prototype.e2e;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AbstractConstantControllerE2ETest extends BasicE2ETest {

    private final String URL_TEMPLATE = "/api/v1/constant";
    private final String SERVICE_ID = "serviceId";

    protected <T> void postConstantEntity(T payloadDto,
                                          String serviceId,
                                          String dataSetPath,
                                          String verifyDataSetPath) {
        loadDataSet(dataSetPath);
        try {
            String jsonPayload = objectMapper.writeValueAsString(payloadDto);

            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(SERVICE_ID, serviceId)
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData(verifyDataSetPath);
    }

    protected <T> void putConstantEntity(T payloadDto,
                                         String serviceId,
                                         String value,
                                         String dataSetPath,
                                         String verifyDataSetPath) {
        loadDataSet(dataSetPath);
        try {
            String jsonPayload = objectMapper.writeValueAsString(payloadDto);

            mockMvc.perform(put(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(SERVICE_ID, serviceId)
                            .param("value", value)
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData(verifyDataSetPath);
    }

    protected <T> void patchConstantEntity(T payloadDto,
                                           String serviceId,
                                           String value,
                                           String dataSetPath,
                                           String verifyDataSetPath) {
        loadDataSet(dataSetPath);
        try {
            String jsonPayload = objectMapper.writeValueAsString(payloadDto);

            mockMvc.perform(patch(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(SERVICE_ID, serviceId)
                            .param("value", value)
                            .content(jsonPayload))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData(verifyDataSetPath);
    }

    protected void deleteConstantEntity(String serviceId, String value, String dataSetPath, String verifyDataSetPath) {
        loadDataSet(dataSetPath);

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .header(SERVICE_ID, serviceId)
                            .param("value", value))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData(verifyDataSetPath);
    }

    protected MvcResult getConstantEntities(String serviceId, String dataSetPath) {
        loadDataSet(dataSetPath);
        try {

            return mockMvc.perform(get(URL_TEMPLATE + "/all")
                            .header(SERVICE_ID, serviceId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected MvcResult getConstantEntity(String serviceId, String value, String dataSetPath) {
        loadDataSet(dataSetPath);
        try {

            return mockMvc.perform(get(URL_TEMPLATE)
                            .header(SERVICE_ID, serviceId)
                            .param("value", value))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> void assertMvcResult(MvcResult mvcResult, T expected) {
        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            T actual = (T) objectMapper.readValue(jsonResponse, expected.getClass());
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    protected <T> void assertMvcListResult(MvcResult mvcResult, List<T> expectedList, TypeReference<List<T>> typeReference) {
        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<T> actualList = objectMapper.readValue(jsonResponse, typeReference);
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

}
