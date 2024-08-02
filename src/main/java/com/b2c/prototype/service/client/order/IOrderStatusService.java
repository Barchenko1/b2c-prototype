package com.b2c.prototype.service.client.order;

import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IOrderStatusService {
    void saveOrderStatus(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateOrderStatus(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deleteOrderStatus(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
