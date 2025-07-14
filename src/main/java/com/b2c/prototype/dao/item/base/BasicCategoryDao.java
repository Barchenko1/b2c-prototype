package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.Category;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCategoryDao extends AbstractTransactionSessionFactoryDao {
    public BasicCategoryDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Category.class);
    }

}
