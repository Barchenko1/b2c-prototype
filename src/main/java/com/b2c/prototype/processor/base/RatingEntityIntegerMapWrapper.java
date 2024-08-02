package com.b2c.prototype.processor.base;

import com.b2c.prototype.modal.client.entity.item.Rating;
import com.b2c.prototype.processor.AbstractEntityIntegerMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class RatingEntityIntegerMapWrapper extends AbstractEntityIntegerMapWrapper<Rating> {

    public RatingEntityIntegerMapWrapper(ISingleEntityDao entityDao,
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
