package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.ITempUserProfileDao;
import com.b2c.prototype.modal.embedded.user.TempUserProfile;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicTempUserProfileDao extends AbstractEntityDao implements ITempUserProfileDao {
    public BasicTempUserProfileDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, TempUserProfile.class);
    }
}
