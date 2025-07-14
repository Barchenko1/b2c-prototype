package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.modal.entity.message.MessageBox;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicMessageBoxDao extends AbstractTransactionSessionFactoryDao {
    public BasicMessageBoxDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, MessageBox.class);
    }
}
