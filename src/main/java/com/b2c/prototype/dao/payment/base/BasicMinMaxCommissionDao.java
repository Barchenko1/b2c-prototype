package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.payment.IMinMaxCommissionDao;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicMinMaxCommissionDao extends AbstractEntityDao implements IMinMaxCommissionDao {
    public BasicMinMaxCommissionDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, MinMaxCommission.class);
    }
}
