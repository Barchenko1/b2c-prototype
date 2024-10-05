package com.b2c.prototype.service.base.delivery;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.dto.update.RequestDeliveryDtoUpdate;

public interface IDeliveryService {
    void saveDelivery(RequestDeliveryDto requestDeliveryDto);
    void updateDelivery(RequestDeliveryDtoUpdate requestDeliveryDtoUpdate);
    void deleteDelivery(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
