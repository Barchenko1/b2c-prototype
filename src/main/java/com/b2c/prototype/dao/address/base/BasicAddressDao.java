package com.b2c.prototype.dao.address.base;

import com.b2c.prototype.modal.entity.address.Address;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicAddressDao extends AbstractTransactionSessionFactoryDao {
    public BasicAddressDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Address.class);
    }
}
