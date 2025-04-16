package com.b2c.prototype.processor.order;

import com.b2c.prototype.modal.dto.payload.order.single.CustomerSingleDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;

import java.util.List;
import java.util.Map;

public interface ICustomerOrderProcessor {
    void saveCustomerOrder(Map<String, String> requestParams, CustomerSingleDeliveryOrderDto orderItemDataSearchFieldEntityDto);
    void updateCustomerOrder(Map<String, String> requestParams, CustomerSingleDeliveryOrderDto orderItemDataSearchFieldEntityDto);
    void deleteCustomerOrder(Map<String, String> requestParams);

    void updateCustomerOrderStatus(Map<String, String> requestParams);

    ResponseCustomerOrderDetails getResponseCustomerOrderDetails(Map<String, String> requestParams);
    List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList(Map<String, String> requestParams);
}
