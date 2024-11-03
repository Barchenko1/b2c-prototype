package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicContactPhoneDao extends AbstractSingleEntityDao implements IContactPhoneDao {
    public BasicContactPhoneDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ContactPhone.class);
    }
}
