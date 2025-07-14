package com.b2c.prototype.dao.user;

import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicContactInfoDao extends AbstractTransactionSessionFactoryDao {
    public BasicContactInfoDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ContactInfo.class);
    }
}
