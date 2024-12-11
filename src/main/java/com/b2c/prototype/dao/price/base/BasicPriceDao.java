package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicPriceDao extends AbstractEntityDao implements IPriceDao {
    public BasicPriceDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Price.class);
    }
}
