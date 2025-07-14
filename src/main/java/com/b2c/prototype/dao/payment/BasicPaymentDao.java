package com.b2c.prototype.dao.payment;

import com.b2c.prototype.modal.entity.payment.Payment;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPaymentDao extends AbstractTransactionSessionFactoryDao {
    public BasicPaymentDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Payment.class);
    }
}
