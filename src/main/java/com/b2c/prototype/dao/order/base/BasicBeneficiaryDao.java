package com.b2c.prototype.dao.order.base;

import com.b2c.prototype.dao.order.IBeneficiaryDao;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicBeneficiaryDao extends AbstractEntityDao implements IBeneficiaryDao {
    public BasicBeneficiaryDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Beneficiary.class);
    }
}
