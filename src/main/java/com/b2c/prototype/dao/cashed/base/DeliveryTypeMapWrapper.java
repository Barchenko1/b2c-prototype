package com.b2c.prototype.dao.cashed.base;

import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.dao.cashed.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class DeliveryTypeMapWrapper extends AbstractEntityStringMapWrapper<DeliveryType> {

    public DeliveryTypeMapWrapper(ISingleEntityDao entityDao,
                                  Map<String, DeliveryType> entityMap) {
        super(entityDao, entityMap, "name");
    }

    @Override
    public DeliveryType getEntity(String value) {
        return super.getOptionalEntity(value)
                .orElseThrow(RuntimeException::new);
    }
}
