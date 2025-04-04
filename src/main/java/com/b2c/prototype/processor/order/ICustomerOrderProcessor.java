package com.b2c.prototype.processor.order;

import com.b2c.prototype.modal.dto.payload.order.CustomerOrderDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCustomerOrderDetails;

import java.util.List;
import java.util.Map;

public interface ICustomerOrderProcessor {
    void saveCustomerOrder(Map<String, String> requestParams, CustomerOrderDto orderItemDataSearchFieldEntityDto);
    void updateCustomerOrder(Map<String, String> requestParams, CustomerOrderDto orderItemDataSearchFieldEntityDto);
    void deleteCustomerOrder(Map<String, String> requestParams);

    ResponseCustomerOrderDetails getResponseCustomerOrderDetails(Map<String, String> requestParams);
    List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList(Map<String, String> requestParams);
}
