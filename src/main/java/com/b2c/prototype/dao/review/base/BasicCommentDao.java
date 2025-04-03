package com.b2c.prototype.dao.review.base;

import com.b2c.prototype.dao.review.ICommentDao;
import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicCommentDao extends AbstractEntityDao implements ICommentDao {
    public BasicCommentDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, ReviewComment.class);
    }
}
