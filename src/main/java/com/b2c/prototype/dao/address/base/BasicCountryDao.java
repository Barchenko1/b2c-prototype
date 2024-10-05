package com.b2c.prototype.dao.address.base;

import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.modal.entity.address.Country;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicCountryDao extends AbstractSingleEntityDao implements ICountryDao {
    public BasicCountryDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Country.class);
    }
}
