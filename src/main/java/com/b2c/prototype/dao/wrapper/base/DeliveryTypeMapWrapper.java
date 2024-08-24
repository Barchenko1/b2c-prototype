package com.b2c.prototype.dao.wrapper.base;

import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.dao.wrapper.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class DeliveryTypeMapWrapper extends AbstractEntityStringMapWrapper<DeliveryType> {

    public DeliveryTypeMapWrapper(ISingleEntityDao entityDao,
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
