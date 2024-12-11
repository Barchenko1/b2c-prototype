package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicMessageTypeDao extends AbstractEntityDao implements IMessageTypeDao {
    public BasicMessageTypeDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, MessageType.class);
    }
}
