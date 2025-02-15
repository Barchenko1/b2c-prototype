package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.dao.store.ICountTypeDao;
import com.b2c.prototype.modal.entity.store.CountType;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCountTypeDao extends AbstractEntityDao implements ICountTypeDao {
    public BasicCountTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, CountType.class);
    }
}
