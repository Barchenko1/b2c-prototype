package com.b2c.prototype.dao.basic;

import com.b2c.prototype.dao.delivery.IAddressDao;
import com.b2c.prototype.modal.client.entity.address.Address;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import org.hibernate.SessionFactory;

public class BasicAddressDao extends AbstractSingleEntityDao implements IAddressDao {
    public BasicAddressDao(SessionFactory sessionFactory) {
        super(sessionFactory, Address.class);
    }
}
