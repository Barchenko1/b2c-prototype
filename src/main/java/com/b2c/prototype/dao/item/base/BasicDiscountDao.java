package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.Discount;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import org.hibernate.SessionFactory;

public class BasicDiscountDao extends AbstractSingleEntityDao implements IDiscountDao {
    public BasicDiscountDao(SessionFactory sessionFactory) {
        super(sessionFactory, Discount.class);
    }
}
