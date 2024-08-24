package com.b2c.prototype.dao.post.base;

import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.dao.post.IPostDao;
import com.tm.core.dao.transitive.AbstractTransitiveSelfEntityDao;
import org.hibernate.SessionFactory;

public class BasicPostDao extends AbstractTransitiveSelfEntityDao implements IPostDao {
    public BasicPostDao(SessionFactory sessionFactory) {
        super(sessionFactory, Post.class);
    }
}
