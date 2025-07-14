package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.tm.core.process.dao.common.session.AbstractSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicContactPhoneDao extends AbstractSessionFactoryDao implements IContactPhoneDao {
    public BasicContactPhoneDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ContactPhone.class);
    }
}
