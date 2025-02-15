package com.b2c.prototype.dao.rating.base;

import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.modal.entity.item.Rating;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.IQueryService;
import org.hibernate.SessionFactory;

public class BasicRatingDao extends AbstractEntityDao implements IRatingDao {
    public BasicRatingDao(SessionFactory sessionFactory, IQueryService queryService) {
        super(sessionFactory, queryService, Rating.class);
    }
}
