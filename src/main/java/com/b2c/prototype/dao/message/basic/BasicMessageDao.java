package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.message.IMessageDao;
import com.b2c.prototype.modal.entity.message.Message;
import com.tm.core.process.dao.common.session.AbstractSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicMessageDao extends AbstractSessionFactoryDao implements IMessageDao {
    public BasicMessageDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Message.class);
    }
}
