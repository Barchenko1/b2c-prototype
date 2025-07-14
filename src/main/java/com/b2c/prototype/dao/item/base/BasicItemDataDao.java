package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.ItemData;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemDataDao extends AbstractTransactionSessionFactoryDao {
    public BasicItemDataDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ItemData.class);
    }
}
