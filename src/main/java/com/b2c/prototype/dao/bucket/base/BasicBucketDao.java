package com.b2c.prototype.dao.bucket.base;

import com.b2c.prototype.modal.entity.bucket.Bucket;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.b2c.prototype.dao.bucket.IBucketDao;
import org.hibernate.SessionFactory;

public class BasicBucketDao extends AbstractGeneralEntityDao implements IBucketDao {
    public BasicBucketDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Bucket.class);
    }
}
