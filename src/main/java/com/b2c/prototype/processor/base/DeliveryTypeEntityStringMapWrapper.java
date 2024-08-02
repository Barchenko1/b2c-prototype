package com.b2c.prototype.processor.base;

import com.b2c.prototype.modal.client.entity.delivery.DeliveryType;
import com.b2c.prototype.processor.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class DeliveryTypeEntityStringMapWrapper extends AbstractEntityStringMapWrapper<DeliveryType> {

    public DeliveryTypeEntityStringMapWrapper(ISingleEntityDao entityDao,
                                              Map<String, DeliveryType> entityMap,
                                              String query) {
        super(entityDao, entityMap, query);
    }

    @Override
    public DeliveryType getEntity(String value) {
        return entityMap.getOrDefault(value,
                (DeliveryType) entityDao.getOptionalEntityBySQLQueryWithParams(query, value)
                        .orElseThrow(RuntimeException::new));
    }
}
