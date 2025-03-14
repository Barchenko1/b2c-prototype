package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionQuantityDao;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantityPrice;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemDataOptionQuantityDao extends AbstractEntityDao implements IItemDataOptionQuantityDao {
    public BasicItemDataOptionQuantityDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ArticularItemQuantityPrice.class);
    }
}
