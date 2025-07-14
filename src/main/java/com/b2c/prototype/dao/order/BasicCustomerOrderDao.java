package com.b2c.prototype.dao.order;

import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCustomerOrderDao extends AbstractTransactionSessionFactoryDao {
    public BasicCustomerOrderDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, DeliveryArticularItemQuantity.class);
    }
}
