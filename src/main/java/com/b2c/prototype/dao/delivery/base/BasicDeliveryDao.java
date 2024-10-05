package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicDeliveryDao extends AbstractGeneralEntityDao implements IDeliveryDao {
    public BasicDeliveryDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Delivery.class);
    }
}
