package com.b2c.prototype.processor.base;

import com.b2c.prototype.modal.client.entity.item.Brand;
import com.b2c.prototype.processor.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class BrandEntityStringMapWrapper extends AbstractEntityStringMapWrapper<Brand> {

    public BrandEntityStringMapWrapper(ISingleEntityDao entityDao,
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
