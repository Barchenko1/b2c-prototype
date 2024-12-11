package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.dao.price.ICurrencyDao;
import com.b2c.prototype.modal.entity.price.Currency;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicCurrencyDao extends AbstractEntityDao implements ICurrencyDao {
    public BasicCurrencyDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Currency.class);
    }
}
