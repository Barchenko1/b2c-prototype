package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicContactInfoDao extends AbstractGeneralEntityDao implements IContactInfoDao {
    public BasicContactInfoDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ContactInfo.class);
    }
}
