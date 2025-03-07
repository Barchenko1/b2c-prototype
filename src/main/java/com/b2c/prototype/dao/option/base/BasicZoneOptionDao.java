package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.dao.option.IZoneOptionDao;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicZoneOptionDao extends AbstractEntityDao implements IZoneOptionDao {
    public BasicZoneOptionDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ZoneOption.class);
    }
}
