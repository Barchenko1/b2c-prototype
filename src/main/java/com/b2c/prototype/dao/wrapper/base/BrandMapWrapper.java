package com.b2c.prototype.dao.wrapper.base;

import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.dao.wrapper.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class BrandMapWrapper extends AbstractEntityStringMapWrapper<Brand> {

    public BrandMapWrapper(ISingleEntityDao entityDao,
                           Map<String, Brand> entityMap,
                           String query) {
        super(entityDao, entityMap, query);
    }

    @Override
    public Brand getEntity(String value) {
        return entityMap.getOrDefault(value,
                (Brand) entityDao.getOptionalEntityBySQLQueryWithParams(query, value)
                        .orElseThrow(RuntimeException::new));
    }


}
