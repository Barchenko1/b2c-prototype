package com.b2c.prototype.processor.base;

import com.b2c.prototype.modal.client.entity.payment.PaymentMethod;
import com.b2c.prototype.processor.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class PaymentMethodEntityStringMapWrapper extends AbstractEntityStringMapWrapper<PaymentMethod> {

    public PaymentMethodEntityStringMapWrapper(ISingleEntityDao entityDao,
                                               Map<String, PaymentMethod> entityMap,
                                               String query) {
        super(entityDao, entityMap, query);
    }

    @Override
    public PaymentMethod getEntity(String value) {
        PaymentMethod paymentMethod = entityMap.get(value);
        if (paymentMethod != null) {
            return paymentMethod;
        }

        return (PaymentMethod) entityDao.getOptionalEntityBySQLQueryWithParams(query, value)
                .orElseThrow(() ->
                        new RuntimeException("Payment method not found for value: " + value));
    }
}
