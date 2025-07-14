package com.b2c.prototype.dao.user;

import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicContactPhoneDao extends AbstractTransactionSessionFactoryDao {
    public BasicContactPhoneDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ContactPhone.class);
    }
}
