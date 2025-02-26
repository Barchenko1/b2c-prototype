package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.delivery.ITimeDurationOptionDao;
import com.b2c.prototype.modal.entity.delivery.TimeDurationOption;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicTimeDurationOptionDao extends AbstractEntityDao implements ITimeDurationOptionDao {
    public BasicTimeDurationOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, TimeDurationOption.class);
    }
}
