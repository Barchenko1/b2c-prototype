package com.b2c.prototype.processor.order;

import com.b2c.prototype.modal.dto.payload.order.PaymentMethodDto;

import java.util.List;
import java.util.Map;

public interface IPaymentMethodProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<PaymentMethodDto> getEntityList(final String location);
    PaymentMethodDto getEntity(final String location, final String value);
}
