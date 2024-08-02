package com.b2c.prototype.service.client.delivery;

import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IDeliveryTypeService {
    void saveDeliveryType(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateDeliveryType(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDto);
    void deleteDeliveryType(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
