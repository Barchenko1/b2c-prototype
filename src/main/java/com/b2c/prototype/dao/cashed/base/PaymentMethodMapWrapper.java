package com.b2c.prototype.dao.cashed.base;

import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.dao.cashed.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class PaymentMethodMapWrapper extends AbstractEntityStringMapWrapper<PaymentMethod> {

    public PaymentMethodMapWrapper(ISingleEntityDao entityDao,
                                   Map<String, PaymentMethod> entityMap) {
        super(entityDao, entityMap, "name");
    }

    @Override
    public PaymentMethod getEntity(String value) {
        PaymentMethod paymentMethod = entityMap.get(value);
        if (paymentMethod != null) {
            return paymentMethod;
        }

        return super.getOptionalEntity(value).orElseThrow(() ->
                new RuntimeException("Payment method not found for value: " + value));
    }
}
