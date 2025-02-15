package com.b2c.prototype.dao.address.base;

import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.modal.entity.address.Country;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCountryDao extends AbstractEntityDao implements ICountryDao {
    public BasicCountryDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Country.class);
    }
}
