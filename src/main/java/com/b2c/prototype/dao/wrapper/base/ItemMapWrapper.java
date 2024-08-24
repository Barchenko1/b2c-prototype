package com.b2c.prototype.dao.wrapper.base;

import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.dao.wrapper.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class ItemMapWrapper extends AbstractEntityStringMapWrapper<ItemStatus> {

    public ItemMapWrapper(ISingleEntityDao entityDao,
                          Map<String, ItemStatus> entityMap,
                          String query) {
        super(entityDao, entityMap, query);
    }

    @Override
    public ItemStatus getEntity(String value) {
        return entityMap.getOrDefault(value,
                (ItemStatus) entityDao.getOptionalEntityBySQLQueryWithParams(query, value)
                        .orElseThrow(RuntimeException::new));
    }
}
