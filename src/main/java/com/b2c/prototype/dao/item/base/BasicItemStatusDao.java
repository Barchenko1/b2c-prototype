package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemStatusDao extends AbstractTransactionSessionFactoryDao {
    public BasicItemStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ArticularStatus.class);
    }
}
