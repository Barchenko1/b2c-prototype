package com.b2c.prototype.dao.bucket.base;

import com.b2c.prototype.modal.entity.bucket.Bucket;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.bucket.IBucketDao;
import org.hibernate.SessionFactory;

public class BasicBucketDao extends AbstractSingleEntityDao implements IBucketDao {
    public BasicBucketDao(SessionFactory sessionFactory) {
        super(sessionFactory, Bucket.class);
    }
}
