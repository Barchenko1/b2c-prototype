package com.b2c.prototype.processor.order;

import com.b2c.prototype.manager.order.ICustomerOrderManager;
import com.b2c.prototype.modal.dto.payload.order.CustomerOrderDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCustomerOrderDetails;

import java.util.List;
import java.util.Map;

public class CustomerOrderProcessor implements ICustomerOrderProcessor {

    private final ICustomerOrderManager orderArticularItemQuantityManager;

    public CustomerOrderProcessor(ICustomerOrderManager orderArticularItemQuantityManager) {
        this.orderArticularItemQuantityManager = orderArticularItemQuantityManager;
    }

    @Override
    public void saveCustomerOrder(Map<String, String> requestParams, CustomerOrderDto orderItemDataSearchFieldEntityDto) {
        orderArticularItemQuantityManager.saveCustomerOrder(orderItemDataSearchFieldEntityDto);
    }

    @Override
    public void updateCustomerOrder(Map<String, String> requestParams, CustomerOrderDto orderItemDataSearchFieldEntityDto) {
        String orderId = requestParams.get("orderId");
        orderArticularItemQuantityManager.updateCustomerOrder(orderId, orderItemDataSearchFieldEntityDto);
    }

    @Override
    public void deleteCustomerOrder(Map<String, String> requestParams) {
        String orderId = requestParams.get("orderId");
        orderArticularItemQuantityManager.deleteCustomerOrder(orderId);
    }

    @Override
    public ResponseCustomerOrderDetails getResponseCustomerOrderDetails(Map<String, String> requestParams) {
        String orderId = requestParams.get("orderId");
        return orderArticularItemQuantityManager.getResponseCustomerOrderDetails(orderId);
    }

    @Override
    public List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList(Map<String, String> requestParams) {
        return orderArticularItemQuantityManager.getResponseCustomerOrderDetailsList();
    }
}
