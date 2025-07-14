package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.modal.entity.message.MessageType;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicMessageTypeDao extends AbstractTransactionSessionFactoryDao {
    public BasicMessageTypeDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, MessageType.class);
    }
}
