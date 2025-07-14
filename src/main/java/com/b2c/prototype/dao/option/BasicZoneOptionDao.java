package com.b2c.prototype.dao.option;

import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicZoneOptionDao extends AbstractTransactionSessionFactoryDao {
    public BasicZoneOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ZoneOption.class);
    }
}
