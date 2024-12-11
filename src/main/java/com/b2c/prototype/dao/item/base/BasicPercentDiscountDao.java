package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IPercentDiscountDao;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicPercentDiscountDao extends AbstractEntityDao implements IPercentDiscountDao {
    public BasicPercentDiscountDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, PercentDiscount.class);
    }
}
