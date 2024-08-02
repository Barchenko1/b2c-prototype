package com.b2c.prototype.service.client.delivery;

import com.b2c.prototype.modal.client.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.client.dto.update.RequestDeliveryDtoUpdate;

public interface IDeliveryService {
    void saveDelivery(RequestDeliveryDto requestDeliveryDto);
    void updateDeliveryAddress(RequestDeliveryDtoUpdate requestDeliveryDtoUpdate);
    void updateDeliveryType(RequestDeliveryDtoUpdate requestDeliveryDtoUpdate);
    void deleteDelivery(RequestDeliveryDto requestDeliveryDto);
}
