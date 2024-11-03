package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicMessageTypeDao extends AbstractSingleEntityDao implements IMessageTypeDao {
    public BasicMessageTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, MessageType.class);
    }
}
