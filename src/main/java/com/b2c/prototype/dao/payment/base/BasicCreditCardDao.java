package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import org.hibernate.SessionFactory;

public class BasicCreditCardDao extends AbstractSingleEntityDao implements ICreditCardDao {
    public BasicCreditCardDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, CreditCard.class);
    }
}
