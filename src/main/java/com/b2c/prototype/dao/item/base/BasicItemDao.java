package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.Item;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemDao extends AbstractEntityDao implements IItemDao {
    public BasicItemDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Item.class);
    }
}
