package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.tm.core.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicCurrencyDiscountDao extends AbstractEntityDao implements ICurrencyDiscountDao {
    public BasicCurrencyDiscountDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, CurrencyDiscount.class);
    }
}
