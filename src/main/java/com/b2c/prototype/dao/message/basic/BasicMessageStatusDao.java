package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicMessageStatusDao extends AbstractSingleEntityDao implements IMessageStatusDao {
    public BasicMessageStatusDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, MessageStatus.class);
    }
}
