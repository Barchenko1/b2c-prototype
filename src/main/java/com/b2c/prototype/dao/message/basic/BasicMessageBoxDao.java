package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.message.IMessageBoxDao;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicMessageBoxDao extends AbstractEntityDao implements IMessageBoxDao {
    public BasicMessageBoxDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, MessageBox.class);
    }
}
