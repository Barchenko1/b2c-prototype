package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.modal.entity.payment.Payment;
import com.tm.core.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicPaymentDao extends AbstractEntityDao implements IPaymentDao {
    public BasicPaymentDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Payment.class);
    }
}
