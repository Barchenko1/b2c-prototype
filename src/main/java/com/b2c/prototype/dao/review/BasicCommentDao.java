package com.b2c.prototype.dao.review;

import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCommentDao extends AbstractTransactionSessionFactoryDao {
    public BasicCommentDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ReviewComment.class);
    }
}
