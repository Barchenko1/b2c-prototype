package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicDeliveryDao extends AbstractSingleEntityDao implements IDeliveryDao {
    public BasicDeliveryDao(SessionFactory sessionFactory) {
        super(sessionFactory, Delivery.class);
    }
}