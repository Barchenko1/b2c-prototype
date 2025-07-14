package com.b2c.prototype.dao.review;

import com.b2c.prototype.modal.entity.review.Review;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicReviewDao extends AbstractTransactionSessionFactoryDao {
    public BasicReviewDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Review.class);
    }
}
