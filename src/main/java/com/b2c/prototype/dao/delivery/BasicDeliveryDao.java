package com.b2c.prototype.dao.delivery;

import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicDeliveryDao extends AbstractTransactionSessionFactoryDao {
    public BasicDeliveryDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Delivery.class);
    }
}
