package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import org.hibernate.SessionFactory;

public class BasicCreditCardDao extends AbstractEntityDao implements ICreditCardDao {
    public BasicCreditCardDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, CreditCard.class);
    }
}
