package com.b2c.prototype.dao.embedded.base;

import com.b2c.prototype.modal.embedded.bucket.Bucket;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import com.b2c.prototype.dao.embedded.IBucketDao;
import org.hibernate.SessionFactory;

public class BasicBucketDao extends AbstractEntityDao implements IBucketDao {
    public BasicBucketDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Bucket.class);
    }
}
