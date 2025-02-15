package com.b2c.prototype.dao.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.modal.entity.address.Address;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicAddressDao extends AbstractEntityDao implements IAddressDao {
    public BasicAddressDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Address.class);
    }
}
