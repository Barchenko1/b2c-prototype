package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicContactPhoneDao extends AbstractEntityDao implements IContactPhoneDao {
    public BasicContactPhoneDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ContactPhone.class);
    }
}
