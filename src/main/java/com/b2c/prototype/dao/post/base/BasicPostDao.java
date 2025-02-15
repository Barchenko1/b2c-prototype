package com.b2c.prototype.dao.post.base;

import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.dao.post.IPostDao;
import com.tm.core.process.dao.transitive.AbstractTransitiveSelfEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPostDao extends AbstractTransitiveSelfEntityDao implements IPostDao {
    public BasicPostDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Post.class);
    }
}
