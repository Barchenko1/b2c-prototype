package com.b2c.prototype.dao.basic;

import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.user.IUserAddressDao;
import org.hibernate.SessionFactory;

public class BasicUserAddressDao extends AbstractSingleEntityDao implements IUserAddressDao {
    public BasicUserAddressDao(SessionFactory sessionFactory, Class<?> clazz) {
        super(sessionFactory, clazz);
    }
}
