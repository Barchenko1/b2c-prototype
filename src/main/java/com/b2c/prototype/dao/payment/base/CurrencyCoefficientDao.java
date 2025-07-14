package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class CurrencyCoefficientDao extends AbstractTransactionSessionFactoryDao {
    public CurrencyCoefficientDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, CurrencyCoefficient.class);
    }
}
