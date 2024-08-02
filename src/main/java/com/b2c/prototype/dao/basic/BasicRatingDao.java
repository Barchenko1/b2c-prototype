package com.b2c.prototype.dao.basic;

import com.b2c.prototype.modal.client.entity.item.Rating;
import com.tm.core.dao.single.AbstractSingleEntityDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import org.hibernate.SessionFactory;

public class BasicRatingDao extends AbstractSingleEntityDao implements IRatingDao {
    public BasicRatingDao(SessionFactory sessionFactory) {
        super(sessionFactory, Rating.class);
    }
}
