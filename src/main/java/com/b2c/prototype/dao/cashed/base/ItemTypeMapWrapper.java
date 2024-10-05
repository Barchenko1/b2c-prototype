package com.b2c.prototype.dao.cashed.base;

import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.dao.cashed.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class ItemTypeMapWrapper extends AbstractEntityStringMapWrapper<ItemType> {

    public ItemTypeMapWrapper(ISingleEntityDao entityDao,
                              Map<String, ItemType> entityMap) {
        super(entityDao, entityMap, "name");
    }

    @Override
    public ItemType getEntity(String value) {
        return super.getOptionalEntity(value)
                        .orElseThrow(RuntimeException::new);
    }
}
