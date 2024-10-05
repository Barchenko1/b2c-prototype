package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.entity.item.Category;
import com.tm.core.dao.transitive.AbstractTransitiveSelfEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
public class BasicCategoryDao extends AbstractTransitiveSelfEntityDao implements ICategoryDao {
    public BasicCategoryDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Category.class);
    }

}
