package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.e2e.util.TestUtil;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerSingleDeliveryOrderControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/order";

    @BeforeEach
    public void cleanUpDatabase() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM customer_single_delivery_order");
            statement.execute("DELETE FROM order_status");
            statement.execute("DELETE FROM contact_info");
            statement.execute("DELETE FROM contact_phone");

            statement.execute("ALTER SEQUENCE contact_info_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE contact_phone_id_seq RESTART WITH 2");
            statement.execute("ALTER SEQUENCE price_id_seq RESTART WITH 3");
            statement.execute("ALTER SEQUENCE discount_id_seq RESTART WITH 2");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: customer_order", e);
        }
    }

    @Test
    void testCreateCustomerOrder() {
        loadDataSet("/datasets/order/customer_order/emptyE2ECustomerOrder.yml");
        try {
            mockMvc.perform(post(URL_TEMPLATE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .params(getMultiValueMap(getRequestParams()))
                            .content(TestUtil.readFile("json/order/input/CustomerSingleDeliveryOrderDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/order/customer_order/testE2ECustomerOrder.yml",
                new String[] {"id", "ORDER_ID", "UPAYMENT_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );

    }

    @Test
    void testPutCustomerOrder() {
        loadDataSet("/datasets/order/customer_order/testE2ECustomerOrder.yml");

        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/order/input/UpdateOrderArticularItemQuantityDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/order/customer_order/updateE2ECustomerOrder.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testChangeCustomerOrderStatus() {
        loadDataSet("/datasets/order/customer_order/testE2ECustomerOrder.yml");

        try {
            mockMvc.perform(put(URL_TEMPLATE)
                            .params(getMultiValueMap(getRequestParams()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.readFile("json/order/input/UpdateOrderArticularItemQuantityDto.json")))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/order/customer_order/updateE2ECustomerOrder.yml",
                new String[] {"id", "option_group_id", "option_item_id", "articular_item_id", "articular_id", "dateOfCreate", "DISCOUNT_ID", "FULLPRICE_ID", "TOTALPRICE_ID"},
                new String[] {"label", "value", "productname", "charSequenceCode"}
        );
    }

    @Test
    void testDeleteCustomerOrder() {
        loadDataSet("/datasets/order/customer_order/testE2ECustomerOrder.yml");

        try {
            mockMvc.perform(delete(URL_TEMPLATE)
                            .params(getMultiValueMap(Map.of("articularId", "3"))))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verifyExpectedData("/datasets/order/customer_order/emptyE2ECustomerOrder.yml");
    }

    @Test
    void testGetCustomerOrder() {
        loadDataSet("/datasets/order/customer_order/testE2ECustomerOrder.yml");
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(get(URL_TEMPLATE)
                            .params(getMultiValueMap(Map.of("articularId", "3")))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ResponseCustomerOrderDetails actual = objectMapper.readValue(jsonResponse, ResponseCustomerOrderDetails.class);
            String expectedResultStr = TestUtil.readFile("json/order/output/ResponseOrderDetails.json");
            ResponseCustomerOrderDetails expected = objectMapper.readValue(expectedResultStr, ResponseCustomerOrderDetails.class);
            assertEquals(expected, actual);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllCustomerOrder() {
        loadDataSet("/datasets/order/customer_order/testE2ECustomerOrder.yml");

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
            List<ResponseCustomerOrderDetails> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            String expectedResultStr = TestUtil.readFile("json/order/output/ResponseOrderDetails.json");
            List<ResponseCustomerOrderDetails> expectedList = objectMapper.readValue(expectedResultStr, new TypeReference<>() {});
            assertEquals(expectedList, actualList);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRequestParams() {
        return Map.of("orderId", "id1");
    }

}
