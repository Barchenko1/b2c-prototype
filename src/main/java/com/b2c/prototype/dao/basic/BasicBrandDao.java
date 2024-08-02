package com.b2c.prototype.dao.basic;

import com.b2c.prototype.modal.client.entity.item.Brand;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.item.IBrandDao;
import org.hibernate.SessionFactory;

public class BasicBrandDao extends AbstractSingleEntityDao implements IBrandDao {
    public BasicBrandDao(SessionFactory sessionFactory) {
        super(sessionFactory, Brand.class);
    }
}
