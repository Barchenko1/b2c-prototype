package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicDeliveryTypeDao extends AbstractEntityDao implements IDeliveryTypeDao {
    public BasicDeliveryTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, DeliveryType.class);
    }
}
