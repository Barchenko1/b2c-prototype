package com.b2c.prototype.processor.base;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.client.entity.item.Category;
import com.b2c.prototype.processor.IEntityStringMapWrapper;

import java.util.Map;

public class CategoryEntityStringMapWrapper implements IEntityStringMapWrapper<Category> {

    private final ICategoryDao categoryDao;
    private final Map<String, Category> categoryMap;
    private final String query;

    public CategoryEntityStringMapWrapper(ICategoryDao categoryDao,
                                          Map<String, Category> categoryMap,
                                          String query) {
        this.categoryDao = categoryDao;
        this.categoryMap = categoryMap;
        this.query = query;
    }

    @Override
    public Category getEntity(String value) {
        return categoryMap.getOrDefault(value,
                (Category) categoryDao.getOptionalEntityBySQLQueryWithParams(query, value)
                        .orElseThrow(RuntimeException::new));
    }

    @Override
    public void putEntity(String key, Category entity) {

    }

    @Override
    public void updateEntity(String oldKey, String key, Category entity) {

    }

    @Override
    public void removeEntity(String key) {

    }
}
