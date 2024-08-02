package com.b2c.prototype.dao.basic;

import com.b2c.prototype.modal.client.entity.review.Review;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.review.IReviewDao;
import org.hibernate.SessionFactory;

public class BasicReviewDao extends AbstractSingleEntityDao implements IReviewDao {
    public BasicReviewDao(SessionFactory sessionFactory) {
        super(sessionFactory, Review.class);
    }
}
