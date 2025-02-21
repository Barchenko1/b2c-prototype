package com.b2c.prototype.manager.order;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.OrderItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderItemDataDto;
import com.b2c.prototype.modal.dto.searchfield.OrderItemDataSearchFieldEntityDto;

import java.util.List;

public interface IOrderItemDataOptionManager {
    void saveOrderItemData(OrderItemDataDto orderItemDto);
    void updateOrderItemData(OrderItemDataSearchFieldEntityDto orderItemDtoUpdate);
    void deleteOrderItemData(OneFieldEntityDto oneFieldEntityDto);

    ResponseOrderItemDataDto getResponseOrderItemData(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseOrderItemDataDto> getResponseOrderItemDataList();
}
