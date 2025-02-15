package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.dao.store.IStoreDao;
import com.b2c.prototype.modal.entity.store.Store;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicStoreDao extends AbstractEntityDao implements IStoreDao {
    public BasicStoreDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Store.class);
    }
}
