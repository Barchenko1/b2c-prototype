package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import org.hibernate.SessionFactory;

public class BasicOrderStatusDao extends AbstractSingleEntityDao implements IOrderStatusDao {
    public BasicOrderStatusDao(SessionFactory sessionFactory) {
        super(sessionFactory, OrderStatus.class);
    }
}