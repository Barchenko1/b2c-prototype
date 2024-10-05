package com.b2c.prototype.dao.cashed.base;

import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.dao.cashed.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class ItemMapWrapper extends AbstractEntityStringMapWrapper<ItemStatus> {

    public ItemMapWrapper(ISingleEntityDao entityDao,
                          Map<String, ItemStatus> entityMap) {
        super(entityDao, entityMap, "name");
    }

    @Override
    public ItemStatus getEntity(String value) {
        return super.getOptionalEntity(value)
                        .orElseThrow(RuntimeException::new);
    }
}
