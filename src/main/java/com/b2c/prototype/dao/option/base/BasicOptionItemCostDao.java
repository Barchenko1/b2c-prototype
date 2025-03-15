package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.dao.option.IOptionItemCostDao;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicOptionItemCostDao extends AbstractEntityDao implements IOptionItemCostDao {
    public BasicOptionItemCostDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, OptionItemCost.class);
    }
}
