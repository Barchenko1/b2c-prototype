package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.modal.entity.payment.Payment;
import com.tm.core.process.dao.common.session.AbstractSessionFactoryDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPaymentDao extends AbstractSessionFactoryDao implements IPaymentDao {
    public BasicPaymentDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Payment.class);
    }
}
