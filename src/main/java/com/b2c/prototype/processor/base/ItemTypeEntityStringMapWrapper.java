package com.b2c.prototype.processor.base;

import com.b2c.prototype.modal.client.entity.item.ItemType;
import com.b2c.prototype.processor.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class ItemTypeEntityStringMapWrapper extends AbstractEntityStringMapWrapper<ItemType> {

    public ItemTypeEntityStringMapWrapper(ISingleEntityDao entityDao,
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
