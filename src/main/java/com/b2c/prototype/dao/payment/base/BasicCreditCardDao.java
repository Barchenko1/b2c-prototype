package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import org.hibernate.SessionFactory;

public class BasicCreditCardDao extends AbstractEntityDao implements ICreditCardDao {
    public BasicCreditCardDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, CreditCard.class);
    }
}
