package com.b2c.prototype.manager.delivery;

import com.b2c.prototype.modal.dto.payload.DeliveryDto;

import java.util.List;

public interface IDeliveryManager {
    void saveUpdateDelivery(String orderId, DeliveryDto deliveryDto);
    void deleteDelivery(String orderId);

    DeliveryDto getDelivery(String orderId);
    List<DeliveryDto> getDeliveries();
}
