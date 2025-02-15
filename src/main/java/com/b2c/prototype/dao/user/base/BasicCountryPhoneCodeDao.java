package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCountryPhoneCodeDao extends AbstractEntityDao implements ICountryPhoneCodeDao {
    public BasicCountryPhoneCodeDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, CountryPhoneCode.class);
    }
}
