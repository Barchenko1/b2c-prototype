package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicContactInfoDao extends AbstractEntityDao implements IContactInfoDao {
    public BasicContactInfoDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ContactInfo.class);
    }
}
