package com.b2c.prototype.service.processor.delivery;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.DeliveryDto;
import com.b2c.prototype.modal.dto.update.DeliverySearchFieldEntityDto;

import java.util.List;

public interface IDeliveryService {
    void saveUpdateDelivery(DeliverySearchFieldEntityDto deliverySearchFieldEntityDto);
    void deleteDelivery(OneFieldEntityDto oneFieldEntityDto);

    DeliveryDto getDelivery(OneFieldEntityDto oneFieldEntityDto);
    List<DeliveryDto> getDeliveries();
}
