package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.order.ICustomerOrderDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCustomerOrderDao extends AbstractEntityDao implements ICustomerOrderDao {
    public BasicCustomerOrderDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, DeliveryArticularItemQuantity.class);
    }
}
