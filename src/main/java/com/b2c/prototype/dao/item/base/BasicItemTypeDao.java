package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.ItemType;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemTypeDao extends AbstractEntityDao implements IItemTypeDao {
    public BasicItemTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ItemType.class);
    }
}
