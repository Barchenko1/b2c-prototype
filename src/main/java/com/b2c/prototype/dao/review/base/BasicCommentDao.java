package com.b2c.prototype.dao.review.base;

import com.b2c.prototype.dao.review.ICommentDao;
import com.b2c.prototype.modal.entity.review.Comment;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.dao.transitive.AbstractTransitiveSelfEntityDao;
import org.hibernate.SessionFactory;

public class BasicCommentDao extends AbstractTransitiveSelfEntityDao implements ICommentDao {
    public BasicCommentDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Comment.class);
    }
}
