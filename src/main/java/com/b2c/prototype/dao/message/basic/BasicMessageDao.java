package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.message.IMessageDao;
import com.b2c.prototype.modal.entity.message.Message;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicMessageDao extends AbstractSingleEntityDao implements IMessageDao {
    public BasicMessageDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Message.class);
    }
}
