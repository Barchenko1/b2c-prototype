package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicItemStatusDao extends AbstractEntityDao implements IItemStatusDao {
    public BasicItemStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ArticularStatus.class);
    }
}
