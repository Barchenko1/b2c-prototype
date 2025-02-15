package com.b2c.prototype.dao.review.base;

import com.b2c.prototype.modal.entity.review.Review;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.b2c.prototype.dao.review.IReviewDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicReviewDao extends AbstractEntityDao implements IReviewDao {
    public BasicReviewDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Review.class);
    }
}
