package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.modal.entity.user.UserDetails;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.user.IUserDetailsDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicUserDetailsDao extends AbstractEntityDao implements IUserDetailsDao {
    public BasicUserDetailsDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, UserDetails.class);
    }
}
