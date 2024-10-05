package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.modal.entity.user.AppUser;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.b2c.prototype.dao.user.IAppUserDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicAppUserDao extends AbstractGeneralEntityDao implements IAppUserDao {
    public BasicAppUserDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, AppUser.class);
    }
}
