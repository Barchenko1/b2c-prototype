package com.b2c.prototype.processor.order;

import com.b2c.prototype.manager.order.IOrderArticularItemQuantityManager;
import com.b2c.prototype.modal.dto.payload.OrderArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderDetails;

import java.util.List;
import java.util.Map;

public class OrderProcessor implements IOrderProcessor {

    private final IOrderArticularItemQuantityManager orderArticularItemQuantityManager;

    public OrderProcessor(IOrderArticularItemQuantityManager orderArticularItemQuantityManager) {
        this.orderArticularItemQuantityManager = orderArticularItemQuantityManager;
    }

    @Override
    public void saveOrderArticularItemQuantity(Map<String, String> requestParams, OrderArticularItemQuantityDto orderItemDataSearchFieldEntityDto) {
        orderArticularItemQuantityManager.saveOrderArticularItemQuantity(orderItemDataSearchFieldEntityDto);
    }

    @Override
    public void updateOrderArticularItemQuantity(Map<String, String> requestParams, OrderArticularItemQuantityDto orderItemDataSearchFieldEntityDto) {
        String orderId = requestParams.get("orderId");
        orderArticularItemQuantityManager.updateOrderArticularItemQuantity(orderId, orderItemDataSearchFieldEntityDto);
    }

    @Override
    public void deleteOrder(Map<String, String> requestParams) {
        String orderId = requestParams.get("orderId");
        orderArticularItemQuantityManager.deleteOrder(orderId);
    }

    @Override
    public ResponseOrderDetails getResponseOrderDetails(Map<String, String> requestParams) {
        String orderId = requestParams.get("orderId");
        return orderArticularItemQuantityManager.getResponseOrderDetails(orderId);
    }

    @Override
    public List<ResponseOrderDetails> getResponseOrderDetailsList(Map<String, String> requestParams) {
        return orderArticularItemQuantityManager.getResponseOrderDetailsList();
    }
}
