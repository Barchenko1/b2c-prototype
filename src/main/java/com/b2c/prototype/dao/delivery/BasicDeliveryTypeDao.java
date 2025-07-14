package com.b2c.prototype.dao.delivery;

import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicDeliveryTypeDao extends AbstractTransactionSessionFactoryDao {
    public BasicDeliveryTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, DeliveryType.class);
    }
}
