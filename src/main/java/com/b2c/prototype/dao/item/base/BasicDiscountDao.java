package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.entity.item.Discount;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicDiscountDao extends AbstractEntityDao implements IDiscountDao {
    public BasicDiscountDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Discount.class);
    }
}
