package com.b2c.prototype.dao.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.modal.entity.address.Address;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicAddressDao extends AbstractSingleEntityDao implements IAddressDao {
    public BasicAddressDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Address.class);
    }
}
