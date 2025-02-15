package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicDeliveryTypeDao extends AbstractEntityDao implements IDeliveryTypeDao {
    public BasicDeliveryTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, DeliveryType.class);
    }
}
