package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.modal.entity.user.AppUser;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.user.IAppUserDao;
import org.hibernate.SessionFactory;

public class BasicAppUserDao extends AbstractSingleEntityDao implements IAppUserDao {
    public BasicAppUserDao(SessionFactory sessionFactory) {
        super(sessionFactory, AppUser.class);
    }
}
