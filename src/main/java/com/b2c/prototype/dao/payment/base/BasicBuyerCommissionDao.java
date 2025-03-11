package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.payment.IBuyerCommissionDao;
import com.b2c.prototype.modal.entity.payment.BuyerCommission;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicBuyerCommissionDao extends AbstractEntityDao implements IBuyerCommissionDao {
    public BasicBuyerCommissionDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, BuyerCommission.class);
    }
}
