package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderItem;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.b2c.prototype.dao.order.IOrderItemDao;
import org.hibernate.SessionFactory;

public class BasicOrderItemDao extends AbstractGeneralEntityDao implements IOrderItemDao {
    public BasicOrderItemDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, OrderItem.class);
    }
}
