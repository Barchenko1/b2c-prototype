package com.b2c.prototype.dao.address.base;

import com.b2c.prototype.modal.entity.address.Country;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCountryDao extends AbstractTransactionSessionFactoryDao {
    public BasicCountryDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Country.class);
    }
}
