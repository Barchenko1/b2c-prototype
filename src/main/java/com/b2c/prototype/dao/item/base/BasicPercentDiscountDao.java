package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IPercentDiscountDao;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicPercentDiscountDao extends AbstractSingleEntityDao implements IPercentDiscountDao {
    public BasicPercentDiscountDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, PercentDiscount.class);
    }
}
