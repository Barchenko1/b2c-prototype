package com.b2c.prototype.manager.order;

import com.b2c.prototype.modal.dto.payload.order.multi.CustomerMultiDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;

import java.util.List;

public interface ICustomerMultipleDeliveryOrderManager {
    void saveCustomerOrder(CustomerMultiDeliveryOrderDto customerMultiDeliveryOrderDto);
    void updateCustomerOrder(String orderId, CustomerMultiDeliveryOrderDto customerMultiDeliveryOrderDto);
    void deleteCustomerOrder(String orderId);

    void updateCustomerOrderStatus(String orderId, String statusValue);

    ResponseCustomerOrderDetails getResponseCustomerOrderDetails(String orderId);
    List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList();
}
