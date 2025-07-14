package com.b2c.prototype.dao.payment;

import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicMinMaxCommissionDao extends AbstractTransactionSessionFactoryDao {
    public BasicMinMaxCommissionDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, MinMaxCommission.class);
    }
}
