package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.item.IBeneficiaryDao;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicBeneficiaryDao extends AbstractEntityDao implements IBeneficiaryDao {
    public BasicBeneficiaryDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Beneficiary.class);
    }
}
