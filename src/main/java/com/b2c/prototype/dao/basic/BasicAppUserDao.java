package com.b2c.prototype.dao.basic;

import com.b2c.prototype.modal.client.entity.user.AppUser;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.user.IAppUserDao;
import org.hibernate.SessionFactory;

public class BasicAppUserDao extends AbstractSingleEntityDao implements IAppUserDao {
    public BasicAppUserDao(SessionFactory sessionFactory) {
        super(sessionFactory, AppUser.class);
    }
}
