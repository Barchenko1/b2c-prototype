package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.modal.entity.store.CountType;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCountTypeDao extends AbstractTransactionSessionFactoryDao {
    public BasicCountTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, CountType.class);
    }
}
