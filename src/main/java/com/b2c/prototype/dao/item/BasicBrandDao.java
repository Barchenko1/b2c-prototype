package com.b2c.prototype.dao.item;

import com.b2c.prototype.modal.entity.item.Brand;
import com.tm.core.process.dao.common.session.AbstractSessionFactoryDao;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

//@Transactional
public class BasicBrandDao extends AbstractTransactionSessionFactoryDao {
    public BasicBrandDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Brand.class);
    }
}
