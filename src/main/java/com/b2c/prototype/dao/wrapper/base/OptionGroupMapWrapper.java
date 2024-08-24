package com.b2c.prototype.dao.wrapper.base;

import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.dao.wrapper.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.List;
import java.util.Map;

public class OptionGroupMapWrapper extends AbstractEntityStringMapWrapper<OptionGroup> {

    public OptionGroupMapWrapper(ISingleEntityDao entityDao,
                                 Map<String, OptionGroup> entityMap,
                                 String query) {
        super(entityDao, entityMap, query);
    }
}
