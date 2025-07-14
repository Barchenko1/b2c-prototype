package com.b2c.prototype.dao.option;

import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicTimeDurationOptionDao extends AbstractTransactionSessionFactoryDao {
    public BasicTimeDurationOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, TimeDurationOption.class);
    }
}
