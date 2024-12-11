package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import org.hibernate.SessionFactory;

public class BasicOrderStatusDao extends AbstractEntityDao implements IOrderStatusDao {
    public BasicOrderStatusDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, OrderStatus.class);
    }
}
