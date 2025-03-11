package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.user.IDeviceDao;
import com.b2c.prototype.modal.entity.user.Device;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicDeviceDao extends AbstractEntityDao implements IDeviceDao {
    public BasicDeviceDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Device.class);
    }
}
