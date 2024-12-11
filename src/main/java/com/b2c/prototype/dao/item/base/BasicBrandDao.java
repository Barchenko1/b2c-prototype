package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.Brand;
import com.tm.core.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicBrandDao extends AbstractEntityDao implements IBrandDao {
    public BasicBrandDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Brand.class);
    }
}
