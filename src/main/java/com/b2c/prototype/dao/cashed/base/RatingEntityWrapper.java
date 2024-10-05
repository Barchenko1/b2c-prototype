package com.b2c.prototype.dao.cashed.base;

import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.dao.cashed.AbstractEntityIntegerMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class RatingEntityWrapper extends AbstractEntityIntegerMapWrapper<Rating> {

    public RatingEntityWrapper(ISingleEntityDao entityDao,
                               Map<Integer, Rating> entityMap) {
        super(entityDao, entityMap, "name");
    }

}
