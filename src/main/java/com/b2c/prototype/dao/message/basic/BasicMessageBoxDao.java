package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.message.IMessageBoxDao;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicMessageBoxDao extends AbstractEntityDao implements IMessageBoxDao {
    public BasicMessageBoxDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, MessageBox.class);
    }
}
