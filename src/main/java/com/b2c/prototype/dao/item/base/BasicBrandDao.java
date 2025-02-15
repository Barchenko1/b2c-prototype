package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.Brand;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicBrandDao extends AbstractEntityDao implements IBrandDao {
    public BasicBrandDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Brand.class);
    }
}
