package com.b2c.prototype.processor.base;

import com.b2c.prototype.modal.client.entity.option.OptionGroup;
import com.b2c.prototype.processor.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class OptionGroupEntityStringMapWrapper extends AbstractEntityStringMapWrapper<OptionGroup> {

    public OptionGroupEntityStringMapWrapper(ISingleEntityDao entityDao,
                                             Map<String, OptionGroup> entityMap,
                                             String query) {
        super(entityDao, entityMap, query);
    }

    @Override
    public OptionGroup getEntity(String value) {
        return entityMap.getOrDefault(value,
                (OptionGroup) entityDao.getOptionalEntityBySQLQueryWithParams(query, value)
                        .orElseThrow(RuntimeException::new));
    }
}
