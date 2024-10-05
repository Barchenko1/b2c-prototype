package com.b2c.prototype.service.base.order;

import com.b2c.prototype.modal.dto.request.RequestOrderItemDto;
import com.b2c.prototype.modal.dto.update.RequestOrderItemDtoUpdate;

public interface IOrderItemService {
    void createOrderItem(RequestOrderItemDto requestOrderItemDto);
    void updateOrderItem(RequestOrderItemDtoUpdate requestOrderItemDtoUpdate);
    void deleteOrderItem(RequestOrderItemDtoUpdate requestOrderItemDtoUpdate);

}
