package com.b2c.prototype.dao.item;

import com.b2c.prototype.modal.entity.item.Discount;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicDiscountDao extends AbstractTransactionSessionFactoryDao {
    public BasicDiscountDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Discount.class);
    }
}
