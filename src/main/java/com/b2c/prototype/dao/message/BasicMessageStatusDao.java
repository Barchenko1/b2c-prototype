package com.b2c.prototype.dao.message;

import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicMessageStatusDao extends AbstractTransactionSessionFactoryDao {
    public BasicMessageStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, MessageStatus.class);
    }
}
