package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicDeliveryDao extends AbstractEntityDao implements IDeliveryDao {
    public BasicDeliveryDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Delivery.class);
    }
}
