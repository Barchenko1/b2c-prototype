package com.b2c.prototype.manager.order;

import com.b2c.prototype.modal.dto.payload.order.single.CustomerSingleDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;

import java.util.List;

public interface ICustomerSingleDeliveryOrderManager {
    void saveCustomerOrder(CustomerSingleDeliveryOrderDto customerSingleDeliveryOrderDto);
    void updateCustomerOrder(String orderId, CustomerSingleDeliveryOrderDto customerSingleDeliveryOrderDto);
    void deleteCustomerOrder(String orderId);

    void updateCustomerOrderStatus(String orderId, String statusValue);

    ResponseCustomerOrderDetails getResponseCustomerOrderDetails(String orderId);
    List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList();
}
