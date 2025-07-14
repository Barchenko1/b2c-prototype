package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.ArticularItemQuantityPrice;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemDataOptionQuantityDao extends AbstractTransactionSessionFactoryDao {
    public BasicItemDataOptionQuantityDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ArticularItemQuantityPrice.class);
    }
}
