package com.b2c.prototype.service.processor.delivery;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.DeliveryDto;
import com.b2c.prototype.modal.dto.update.DeliveryDtoUpdate;

public interface IDeliveryService {
    void saveDelivery(DeliveryDto deliveryDto);
    void updateDelivery(DeliveryDtoUpdate requestDeliveryDtoUpdate);
    void deleteDelivery(OneFieldEntityDto oneFieldEntityDto);
}
