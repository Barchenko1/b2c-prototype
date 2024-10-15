package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicCurrencyDiscountDao extends AbstractSingleEntityDao implements ICurrencyDiscountDao {
    public BasicCurrencyDiscountDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, CurrencyDiscount.class);
    }
}
