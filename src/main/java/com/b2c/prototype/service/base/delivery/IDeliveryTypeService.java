package com.b2c.prototype.service.base.delivery;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IDeliveryTypeService {
    void saveDeliveryType(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateDeliveryType(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDto);
    void deleteDeliveryType(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
