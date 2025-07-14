package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.modal.entity.price.Currency;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCurrencyDao extends AbstractTransactionSessionFactoryDao {
    public BasicCurrencyDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Currency.class);
    }
}
