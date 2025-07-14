package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCountryPhoneCodeDao extends AbstractTransactionSessionFactoryDao {
    public BasicCountryPhoneCodeDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, CountryPhoneCode.class);
    }
}
