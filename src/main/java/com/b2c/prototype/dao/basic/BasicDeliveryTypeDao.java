package com.b2c.prototype.dao.basic;

import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.modal.client.entity.delivery.DeliveryType;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicDeliveryTypeDao extends AbstractSingleEntityDao implements IDeliveryTypeDao {
    public BasicDeliveryTypeDao(SessionFactory sessionFactory) {
        super(sessionFactory, DeliveryType.class);
    }
}
