package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicContactPhoneDao extends AbstractEntityDao implements IContactPhoneDao {
    public BasicContactPhoneDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ContactPhone.class);
    }
}
