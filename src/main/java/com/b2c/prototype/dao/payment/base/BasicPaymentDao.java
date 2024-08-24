package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.modal.entity.payment.Payment;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import org.hibernate.SessionFactory;

public class BasicPaymentDao extends AbstractSingleEntityDao implements IPaymentDao {
    public BasicPaymentDao(SessionFactory sessionFactory) {
        super(sessionFactory, Payment.class);
    }
}
