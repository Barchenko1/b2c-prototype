package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicOptionGroupDao extends AbstractTransactionSessionFactoryDao {
    public BasicOptionGroupDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, OptionGroup.class);
    }
}
