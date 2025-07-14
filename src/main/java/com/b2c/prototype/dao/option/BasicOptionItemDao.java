package com.b2c.prototype.dao.option;

import com.b2c.prototype.modal.entity.option.OptionItem;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicOptionItemDao extends AbstractTransactionSessionFactoryDao {
    public BasicOptionItemDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, OptionItem.class);
    }
}
