package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicOrderStatusDao extends AbstractEntityDao implements IOrderStatusDao {
    public BasicOrderStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, OrderStatus.class);
    }
}
