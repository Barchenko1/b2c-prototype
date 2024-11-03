package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.ITempUserProfileDao;
import com.b2c.prototype.modal.embedded.item.TempItemData;
import com.b2c.prototype.modal.embedded.user.TempUserProfile;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicTempUserProfileDao extends AbstractSingleEntityDao implements ITempUserProfileDao {
    public BasicTempUserProfileDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, TempUserProfile.class);
    }
}
