package com.b2c.prototype.dao.review.base;

import com.b2c.prototype.modal.entity.review.Review;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.review.IReviewDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicReviewDao extends AbstractSingleEntityDao implements IReviewDao {
    public BasicReviewDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Review.class);
    }
}
