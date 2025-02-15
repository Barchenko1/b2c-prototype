package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.modal.entity.option.OptionItem;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicOptionItemDao extends AbstractEntityDao implements IOptionItemDao {
    public BasicOptionItemDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, OptionItem.class);
    }
}
