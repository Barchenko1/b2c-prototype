package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicMessageStatusDao extends AbstractEntityDao implements IMessageStatusDao {
    public BasicMessageStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, MessageStatus.class);
    }
}
