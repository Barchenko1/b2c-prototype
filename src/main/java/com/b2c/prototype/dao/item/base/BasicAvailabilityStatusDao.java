package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IAvailabilityStatusDao;
import com.b2c.prototype.modal.entity.item.AvailabilityStatus;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicAvailabilityStatusDao extends AbstractEntityDao implements IAvailabilityStatusDao {
    public BasicAvailabilityStatusDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, AvailabilityStatus.class);
    }
}
