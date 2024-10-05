package com.b2c.prototype.dao.cashed.base;

import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.factory.ParameterFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CategoryMapWrapper implements IEntityStringMapWrapper<Category> {

    private final ICategoryDao categoryDao;
    private final Map<String, Category> categoryMap;
    private final IParameterFactory parameterFactory;

    public CategoryMapWrapper(ICategoryDao categoryDao,
                              Map<String, Category> categoryMap) {
        this.categoryDao = categoryDao;
        this.categoryMap = categoryMap;
        this.parameterFactory = new ParameterFactory();
    }

    @Override
    public Category getEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter("name", value);
        return categoryMap.getOrDefault(value,
                (Category) categoryDao.getOptionalTransitiveSelfEntity(parameter)
                        .orElseThrow(RuntimeException::new));
    }

    @Override
    public Optional<Category> getOptionalEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter("name", value);
        return Optional.of(categoryMap.getOrDefault(value, categoryDao.getTransitiveSelfEntity(parameter)));
    }

    @Override
    public List<Category> getEntityList(List<String> values) {

        return List.of();
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
