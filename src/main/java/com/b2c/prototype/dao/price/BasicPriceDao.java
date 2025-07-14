package com.b2c.prototype.dao.price;

import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPriceDao extends AbstractTransactionSessionFactoryDao {
    public BasicPriceDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Price.class);
    }
}
