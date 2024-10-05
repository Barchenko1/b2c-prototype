package com.b2c.prototype.dao.cashed.base;

import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.dao.cashed.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class OptionGroupMapWrapper extends AbstractEntityStringMapWrapper<OptionGroup> {

    public OptionGroupMapWrapper(ISingleEntityDao entityDao,
                                 Map<String, OptionGroup> entityMap) {
        super(entityDao, entityMap, "name");
    }
}
