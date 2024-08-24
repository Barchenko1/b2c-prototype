package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderItem;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.order.IOrderItemDao;
import org.hibernate.SessionFactory;

public class BasicOrderItemDao extends AbstractSingleEntityDao implements IOrderItemDao {
    public BasicOrderItemDao(SessionFactory sessionFactory) {
        super(sessionFactory, OrderItem.class);
    }
}
