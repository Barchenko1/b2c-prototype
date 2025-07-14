package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.modal.entity.user.UserDetails;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicUserDetailsDao extends AbstractTransactionSessionFactoryDao {
    public BasicUserDetailsDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, UserDetails.class);
    }
}
