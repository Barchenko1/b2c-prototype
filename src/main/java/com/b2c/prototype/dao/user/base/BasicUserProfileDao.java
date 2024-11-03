package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.modal.entity.user.UserProfile;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicUserProfileDao extends AbstractGeneralEntityDao implements IUserProfileDao {
    public BasicUserProfileDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, UserProfile.class);
    }
}