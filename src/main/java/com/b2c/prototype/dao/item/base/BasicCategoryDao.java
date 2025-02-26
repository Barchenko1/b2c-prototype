package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.base.AbstractConstantEntity;
import com.b2c.prototype.modal.entity.item.Category;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.transitive.AbstractTransitiveSelfEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCategoryDao extends AbstractEntityDao implements ICategoryDao {
    public BasicCategoryDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Category.class);
    }

}
