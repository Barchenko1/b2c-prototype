package com.b2c.prototype.dao.post.base;

import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.dao.post.IPostDao;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicPostDao extends AbstractEntityDao implements IPostDao {
    public BasicPostDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Post.class);
    }
}
