package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicCountryPhoneCodeDao extends AbstractEntityDao implements ICountryPhoneCodeDao {
    public BasicCountryPhoneCodeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, CountryPhoneCode.class);
    }
}
