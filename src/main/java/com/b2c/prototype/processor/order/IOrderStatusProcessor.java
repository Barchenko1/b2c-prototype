package com.b2c.prototype.processor.order;

import com.b2c.prototype.modal.dto.payload.order.OrderStatusDto;

import java.util.List;
import java.util.Map;

public interface IOrderStatusProcessor {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<OrderStatusDto> getEntityList(final String location);
    OrderStatusDto getEntity(final String location, final String value);
}
