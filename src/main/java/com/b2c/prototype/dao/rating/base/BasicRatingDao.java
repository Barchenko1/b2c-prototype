package com.b2c.prototype.dao.rating.base;

import com.b2c.prototype.modal.entity.review.Rating;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicRatingDao extends AbstractTransactionSessionFactoryDao {
    public BasicRatingDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Rating.class);
    }
}
