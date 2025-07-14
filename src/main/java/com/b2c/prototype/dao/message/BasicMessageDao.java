package com.b2c.prototype.dao.message;

import com.b2c.prototype.modal.entity.message.Message;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicMessageDao extends AbstractTransactionSessionFactoryDao {
    public BasicMessageDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Message.class);
    }
}
