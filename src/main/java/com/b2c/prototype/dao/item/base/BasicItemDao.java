package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.Item;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemDao extends AbstractTransactionSessionFactoryDao {
    public BasicItemDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Item.class);
    }
}
