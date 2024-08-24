package com.b2c.prototype.dao.wrapper.base;

import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.dao.wrapper.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class PaymentMethodMapWrapper extends AbstractEntityStringMapWrapper<PaymentMethod> {

    public PaymentMethodMapWrapper(ISingleEntityDao entityDao,
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
