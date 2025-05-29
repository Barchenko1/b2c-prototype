package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.payment.ICurrencyCoefficientDao;
import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class CurrencyCoefficientDao extends AbstractEntityDao implements ICurrencyCoefficientDao {
    public CurrencyCoefficientDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, CurrencyCoefficient.class);
    }
}
