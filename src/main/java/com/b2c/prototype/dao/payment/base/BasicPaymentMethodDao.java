package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPaymentMethodDao extends AbstractEntityDao implements IPaymentMethodDao {
    public BasicPaymentMethodDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, PaymentMethod.class);
    }
}
