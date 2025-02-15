package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicOptionGroupDao extends AbstractEntityDao implements IOptionGroupDao {
    public BasicOptionGroupDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, OptionGroup.class);
    }
}
