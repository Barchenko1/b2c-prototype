package com.b2c.prototype.service.client.order;

import com.b2c.prototype.modal.client.dto.request.RequestOrderItemDto;

public interface IOrderItemService {
    void createOrderItem(RequestOrderItemDto requestOrderItemDto);
    void updateOrderItemByOrderId(String orderId);
    void deleteOrderItemByOrderId(String orderId);

}
