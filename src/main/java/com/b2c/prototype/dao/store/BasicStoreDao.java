package com.b2c.prototype.dao.store;

import com.b2c.prototype.modal.entity.store.Store;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicStoreDao extends AbstractTransactionSessionFactoryDao {
    public BasicStoreDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Store.class);
    }
}
