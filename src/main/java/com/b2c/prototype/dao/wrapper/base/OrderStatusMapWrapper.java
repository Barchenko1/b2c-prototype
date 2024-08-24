package com.b2c.prototype.dao.wrapper.base;

import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.dao.wrapper.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class OrderStatusMapWrapper extends AbstractEntityStringMapWrapper<OrderStatus> {

    public OrderStatusMapWrapper(ISingleEntityDao entityDao,
                                 Map<String, OrderStatus> entityMap,
                                 String query) {
        super(entityDao, entityMap, query);
    }

    @Override
    public OrderStatus getEntity(String value) {
        return entityMap.getOrDefault(value,
                (OrderStatus) entityDao.getOptionalEntityBySQLQueryWithParams(query, value)
                        .orElseThrow(RuntimeException::new));
    }

}
