package com.b2c.prototype.dao.rating.base;

import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.modal.entity.item.Rating;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.hibernate.SessionFactory;

public class BasicRatingDao extends AbstractEntityDao implements IRatingDao {
    public BasicRatingDao(SessionFactory sessionFactory, IEntityIdentifierDao entityIdentifierDao) {
        super(sessionFactory, entityIdentifierDao, Rating.class);
    }
}
