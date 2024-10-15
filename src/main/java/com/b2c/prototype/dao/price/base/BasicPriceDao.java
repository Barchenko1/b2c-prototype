package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicPriceDao extends AbstractSingleEntityDao implements IPriceDao {
    public BasicPriceDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Price.class);
    }
}
