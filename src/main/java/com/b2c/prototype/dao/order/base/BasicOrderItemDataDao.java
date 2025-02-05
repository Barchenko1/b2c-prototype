package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.b2c.prototype.dao.order.IOrderItemDataDao;
import org.hibernate.SessionFactory;

public class BasicOrderItemDataDao extends AbstractEntityDao implements IOrderItemDataDao {
    public BasicOrderItemDataDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, OrderArticularItem.class);
    }
}
