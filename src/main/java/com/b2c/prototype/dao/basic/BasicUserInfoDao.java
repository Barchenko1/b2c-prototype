package com.b2c.prototype.dao.basic;

import com.b2c.prototype.dao.userinfo.IUserInfoDao;
import com.b2c.prototype.modal.client.entity.user.UserInfo;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicUserInfoDao extends AbstractSingleEntityDao implements IUserInfoDao {
    public BasicUserInfoDao(SessionFactory sessionFactory) {
        super(sessionFactory, UserInfo.class);
    }
}
