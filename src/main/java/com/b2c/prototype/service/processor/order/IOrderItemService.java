package com.b2c.prototype.service.processor.order;

import com.b2c.prototype.modal.dto.request.OrderItemDto;
import com.b2c.prototype.modal.dto.update.OrderItemDtoUpdate;

public interface IOrderItemService {
    void createOrderItem(OrderItemDto orderItemDto);
    void updateOrderItem(OrderItemDtoUpdate requestOrderItemDtoUpdate);
    void deleteOrderItem(OrderItemDtoUpdate requestOrderItemDtoUpdate);

}
