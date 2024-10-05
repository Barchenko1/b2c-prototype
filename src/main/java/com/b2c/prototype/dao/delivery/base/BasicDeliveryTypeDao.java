package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicDeliveryTypeDao extends AbstractSingleEntityDao implements IDeliveryTypeDao {
    public BasicDeliveryTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, DeliveryType.class);
    }
}
