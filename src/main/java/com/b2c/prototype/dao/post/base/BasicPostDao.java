package com.b2c.prototype.dao.post.base;

import com.b2c.prototype.modal.entity.post.Post;
import com.tm.core.process.dao.common.session.AbstractTransactionSessionFactoryDao;
import com.tm.core.process.dao.query.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPostDao extends AbstractTransactionSessionFactoryDao {
    public BasicPostDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Post.class);
    }
}
