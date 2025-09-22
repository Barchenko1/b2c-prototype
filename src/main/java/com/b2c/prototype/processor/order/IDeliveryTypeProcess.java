package com.b2c.prototype.processor.order;

import com.b2c.prototype.modal.dto.payload.order.DeliveryTypeDto;

import java.util.List;
import java.util.Map;

public interface IDeliveryTypeProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<DeliveryTypeDto> getEntityList(final String location);
    DeliveryTypeDto getEntity(final String location, final String value);
}
