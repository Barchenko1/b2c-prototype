package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.ITempUserProfileDao;
import com.b2c.prototype.modal.embedded.user.TempUserProfile;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicTempUserProfileDao extends AbstractEntityDao implements ITempUserProfileDao {
    public BasicTempUserProfileDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, TempUserProfile.class);
    }
}
