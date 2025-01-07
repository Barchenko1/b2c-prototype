package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicContactInfoDao extends AbstractEntityDao implements IContactInfoDao {
    public BasicContactInfoDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, ContactInfo.class);
    }
}
