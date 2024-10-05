package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.IUserInfoDao;
import com.b2c.prototype.modal.entity.user.UserInfo;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicUserInfoDao extends AbstractSingleEntityDao implements IUserInfoDao {
    public BasicUserInfoDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, UserInfo.class);
    }
}
