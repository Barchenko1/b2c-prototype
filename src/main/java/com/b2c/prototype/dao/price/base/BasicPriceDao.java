package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.process.dao.common.session.AbstractSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPriceDao extends AbstractSessionFactoryDao implements IPriceDao {
    public BasicPriceDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Price.class);
    }
}
