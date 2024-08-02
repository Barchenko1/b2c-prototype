package com.b2c.prototype.processor.base;

import com.b2c.prototype.modal.client.entity.item.ItemStatus;
import com.b2c.prototype.processor.AbstractEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public class ItemStatusEntityStringMapWrapper extends AbstractEntityStringMapWrapper<ItemStatus> {

    public ItemStatusEntityStringMapWrapper(ISingleEntityDao entityDao,
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
