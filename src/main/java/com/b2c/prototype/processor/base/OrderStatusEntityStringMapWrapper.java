package com.b2c.prototype.processor.base;

import com.b2c.prototype.modal.client.entity.order.OrderStatus;
import com.b2c.prototype.processor.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class OrderStatusEntityStringMapWrapper extends AbstractEntityStringMapWrapper<OrderStatus> {

    public OrderStatusEntityStringMapWrapper(ISingleEntityDao entityDao,
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
