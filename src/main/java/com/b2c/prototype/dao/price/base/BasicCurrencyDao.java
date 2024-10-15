package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.dao.price.ICurrencyDao;
import com.b2c.prototype.modal.entity.price.Currency;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicCurrencyDao extends AbstractSingleEntityDao implements ICurrencyDao {
    public BasicCurrencyDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Currency.class);
    }
}
