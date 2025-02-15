package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPriceDao extends AbstractEntityDao implements IPriceDao {
    public BasicPriceDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Price.class);
    }
}
