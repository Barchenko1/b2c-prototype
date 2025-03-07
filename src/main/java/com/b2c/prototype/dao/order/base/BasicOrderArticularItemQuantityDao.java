package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderArticularItemQuantity;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicOrderArticularItemQuantityDao extends AbstractEntityDao implements IOrderItemDataDao {
    public BasicOrderArticularItemQuantityDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, OrderArticularItemQuantity.class);
    }
}
