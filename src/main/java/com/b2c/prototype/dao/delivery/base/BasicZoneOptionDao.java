package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.delivery.IZoneOptionDao;
import com.b2c.prototype.modal.entity.delivery.ZoneOption;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicZoneOptionDao extends AbstractEntityDao implements IZoneOptionDao {
    public BasicZoneOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ZoneOption.class);
    }
}
