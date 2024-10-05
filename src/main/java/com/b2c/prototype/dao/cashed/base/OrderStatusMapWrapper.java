package com.b2c.prototype.dao.cashed.base;

import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.dao.cashed.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class OrderStatusMapWrapper extends AbstractEntityStringMapWrapper<OrderStatus> {

    public OrderStatusMapWrapper(ISingleEntityDao entityDao,
                                 Map<String, OrderStatus> entityMap) {
        super(entityDao, entityMap, "name");
    }

    @Override
    public OrderStatus getEntity(String value) {
        return super.getOptionalEntity(value)
                        .orElseThrow(RuntimeException::new);
    }

}
