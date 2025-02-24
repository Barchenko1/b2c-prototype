package com.b2c.prototype.manager.order;

import com.b2c.prototype.modal.dto.payload.OrderArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderDetails;

import java.util.List;

public interface IOrderArticularItemQuantityManager {
    void saveOrderArticularItemQuantity(OrderArticularItemQuantityDto orderArticularItemQuantityDto);
    void updateOrderArticularItemQuantity(String orderId, OrderArticularItemQuantityDto orderArticularItemQuantityDto);
    void deleteOrder(String orderId);

    ResponseOrderDetails getResponseOrderDetails(String orderId);
    List<ResponseOrderDetails> getResponseOrderDetailsList();
}
