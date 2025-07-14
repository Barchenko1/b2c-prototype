package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPaymentMethodDao extends AbstractTransactionSessionFactoryDao {
    public BasicPaymentMethodDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, PaymentMethod.class);
    }
}
