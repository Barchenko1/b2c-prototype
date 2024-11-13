package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.dao.store.ICountTypeDao;
import com.b2c.prototype.modal.entity.store.CountType;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicCountTypeDao extends AbstractSingleEntityDao implements ICountTypeDao {
    public BasicCountTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, CountType.class);
    }
}
