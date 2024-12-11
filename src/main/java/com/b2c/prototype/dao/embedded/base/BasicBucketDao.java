package com.b2c.prototype.dao.embedded.base;

import com.b2c.prototype.modal.embedded.bucket.Bucket;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.b2c.prototype.dao.embedded.IBucketDao;
import org.hibernate.SessionFactory;

public class BasicBucketDao extends AbstractEntityDao implements IBucketDao {
    public BasicBucketDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Bucket.class);
    }
}
