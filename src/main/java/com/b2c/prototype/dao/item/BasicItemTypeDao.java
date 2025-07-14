package com.b2c.prototype.dao.item;

import com.b2c.prototype.modal.entity.item.ItemType;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemTypeDao extends AbstractTransactionSessionFactoryDao {
    public BasicItemTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ItemType.class);
    }
}
