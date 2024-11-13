package com.b2c.prototype.service.processor.delivery;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.dto.update.DeliveryDtoUpdate;

public interface IDeliveryService {
    void saveDelivery(RequestDeliveryDto requestDeliveryDto);
    void updateDelivery(DeliveryDtoUpdate requestDeliveryDtoUpdate);
    void deleteDelivery(OneFieldEntityDto oneFieldEntityDto);
}
