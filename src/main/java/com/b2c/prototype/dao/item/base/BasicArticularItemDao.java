package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicArticularItemDao extends AbstractEntityDao implements IItemDataOptionDao {
    public BasicArticularItemDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ArticularItem.class);
    }
}
