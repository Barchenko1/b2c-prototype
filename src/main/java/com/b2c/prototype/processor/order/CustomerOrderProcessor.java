package com.b2c.prototype.processor.order;

import com.b2c.prototype.manager.order.ICustomerSingleDeliveryOrderManager;
import com.b2c.prototype.modal.dto.payload.order.single.CustomerSingleDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;

import java.util.List;
import java.util.Map;

public class CustomerOrderProcessor implements ICustomerOrderProcessor {

    private final ICustomerSingleDeliveryOrderManager customerOrderManager;

    public CustomerOrderProcessor(ICustomerSingleDeliveryOrderManager customerOrderManager) {
        this.customerOrderManager = customerOrderManager;
    }

    @Override
    public void saveCustomerOrder(Map<String, String> requestParams, CustomerSingleDeliveryOrderDto orderItemDataSearchFieldEntityDto) {
        customerOrderManager.saveCustomerOrder(orderItemDataSearchFieldEntityDto);
    }

    @Override
    public void updateCustomerOrder(Map<String, String> requestParams, CustomerSingleDeliveryOrderDto orderItemDataSearchFieldEntityDto) {
        String orderId = requestParams.get("orderId");
        customerOrderManager.updateCustomerOrder(orderId, orderItemDataSearchFieldEntityDto);
    }

    @Override
    public void deleteCustomerOrder(Map<String, String> requestParams) {
        String orderId = requestParams.get("orderId");
        customerOrderManager.deleteCustomerOrder(orderId);
    }

    @Override
    public void updateCustomerOrderStatus(Map<String, String> requestParams) {
        String orderId = requestParams.get("orderId");
        String status = requestParams.get("status");
        customerOrderManager.updateCustomerOrderStatus(orderId, status);
    }

    @Override
    public ResponseCustomerOrderDetails getResponseCustomerOrderDetails(Map<String, String> requestParams) {
        String orderId = requestParams.get("orderId");
        return customerOrderManager.getResponseCustomerOrderDetails(orderId);
    }

    @Override
    public List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList(Map<String, String> requestParams) {
        return customerOrderManager.getResponseCustomerOrderDetailsList();
    }
}
