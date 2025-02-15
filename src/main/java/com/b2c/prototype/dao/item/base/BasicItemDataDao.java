package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemDataDao extends AbstractEntityDao implements IItemDataDao {
    public BasicItemDataDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ItemData.class);
    }
}
