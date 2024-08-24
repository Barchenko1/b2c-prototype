package com.b2c.prototype.dao.wrapper.base;

import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.dao.wrapper.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class ItemTypeMapWrapper extends AbstractEntityStringMapWrapper<ItemType> {

    public ItemTypeMapWrapper(ISingleEntityDao entityDao,
                              Map<String, ItemType> entityMap,
                              String query) {
        super(entityDao, entityMap, query);
    }

    @Override
    public ItemType getEntity(String value) {
        return entityMap.getOrDefault(value,
                (ItemType) entityDao.getOptionalEntityBySQLQueryWithParams(query, value)
                        .orElseThrow(RuntimeException::new));
    }
}
