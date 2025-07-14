package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderStatus;

import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicOrderStatusDao extends AbstractTransactionSessionFactoryDao {
    public BasicOrderStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, OrderStatus.class);
    }
}
