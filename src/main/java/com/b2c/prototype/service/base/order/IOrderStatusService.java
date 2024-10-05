package com.b2c.prototype.service.base.order;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IOrderStatusService {
    void saveOrderStatus(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateOrderStatus(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deleteOrderStatus(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
