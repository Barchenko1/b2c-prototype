package com.b2c.prototype.manager.order;

import com.b2c.prototype.modal.dto.payload.order.CustomerOrderDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCustomerOrderDetails;

import java.util.List;

public interface ICustomerOrderManager {
    void saveCustomerOrder(CustomerOrderDto customerOrderDto);
    void updateCustomerOrder(String orderId, CustomerOrderDto customerOrderDto);
    void deleteCustomerOrder(String orderId);

    ResponseCustomerOrderDetails getResponseCustomerOrderDetails(String orderId);
    List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList();
}
