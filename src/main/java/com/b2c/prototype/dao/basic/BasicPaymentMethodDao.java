package com.b2c.prototype.dao.basic;

import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.modal.client.entity.payment.PaymentMethod;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicPaymentMethodDao extends AbstractSingleEntityDao implements IPaymentMethodDao {
    public BasicPaymentMethodDao(SessionFactory sessionFactory) {
        super(sessionFactory, PaymentMethod.class);
    }
}
