package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.manager.payment.ICommissionDao;
import com.b2c.prototype.modal.entity.payment.SellerCommission;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicSellerCommissionDao extends AbstractEntityDao implements ICommissionDao {
    public BasicSellerCommissionDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, SellerCommission.class);
    }
}
