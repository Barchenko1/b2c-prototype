package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.dao.store.IStoreDao;
import com.b2c.prototype.modal.entity.store.Store;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicStoreDao extends AbstractSingleEntityDao implements IStoreDao {
    public BasicStoreDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Store.class);
    }
}
