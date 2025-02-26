package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.modal.entity.user.UserDetails;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicUserProfileDao extends AbstractEntityDao implements IUserProfileDao {
    public BasicUserProfileDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, UserDetails.class);
    }
}
