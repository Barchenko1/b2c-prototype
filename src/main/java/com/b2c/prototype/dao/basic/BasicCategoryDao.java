package com.b2c.prototype.dao.basic;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.client.entity.item.Category;
import com.tm.core.dao.transitive.AbstractTransitiveSelfEntityDao;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
public class BasicCategoryDao extends AbstractTransitiveSelfEntityDao implements ICategoryDao {
    public BasicCategoryDao(SessionFactory sessionFactory) {
        super(sessionFactory, Category.class);
    }

}
