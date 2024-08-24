package com.b2c.prototype.dao.wrapper.base;

import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.dao.wrapper.AbstractEntityIntegerMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class RatingEntityWrapper extends AbstractEntityIntegerMapWrapper<Rating> {

    public RatingEntityWrapper(ISingleEntityDao entityDao,
                               Map<Integer, Rating> entityMap,
                               String query) {
        super(entityDao, entityMap, query);
    }

    @Override
    public Rating getEntity(Integer value) {
        return entityMap.getOrDefault(value,
                (Rating) entityDao.getOptionalEntityBySQLQueryWithParams(query, value)
                        .orElseThrow(RuntimeException::new));
    }
}
