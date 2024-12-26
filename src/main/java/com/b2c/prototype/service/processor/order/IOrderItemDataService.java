package com.b2c.prototype.service.processor.order;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.OrderItemDataDto;
import com.b2c.prototype.modal.dto.update.OrderItemDataDtoUpdate;

import java.util.List;

public interface IOrderItemDataService {
    void saveOrderItemData(OrderItemDataDto orderItemDto);
    void updateOrderItemData(OrderItemDataDtoUpdate orderItemDtoUpdate);
    void deleteOrderItemData(OneFieldEntityDto oneFieldEntityDto);

    OrderItemDataDto getOrderItemData(OneFieldEntityDto oneFieldEntityDto);
    List<OrderItemDataDto> getOrderItemListData(OneFieldEntityDto oneFieldEntityDto);
}
