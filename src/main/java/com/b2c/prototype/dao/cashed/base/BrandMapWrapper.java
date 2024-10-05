package com.b2c.prototype.dao.cashed.base;

import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.dao.cashed.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class BrandMapWrapper extends AbstractEntityStringMapWrapper<Brand> {

    public BrandMapWrapper(ISingleEntityDao entityDao,
                           Map<String, Brand> entityMap) {
        super(entityDao, entityMap, "name");
    }

    @Override
    public Brand getEntity(String value) {
        return super.getOptionalEntity(value)
                        .orElseThrow(RuntimeException::new);
    }


}
