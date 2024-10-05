package com.b2c.prototype.dao.cashed;

import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractEntityStringMapWrapper<E> implements IEntityStringMapWrapper<E> {

    protected final ISingleEntityDao entityDao;
    protected final Map<String, E> entityMap;
    protected final Parameter parameter;

    public AbstractEntityStringMapWrapper(ISingleEntityDao entityDao,
                                          Map<String, E> entityMap,
                                          String key) {
        this.entityDao = entityDao;
        this.entityMap = entityMap;
        this.parameter = new Parameter(key, null);
    }

    @Override
    public E getEntity(String value) {
        return getEntityFromMapOrFromDb(value);
    }

    @Override
    public Optional<E> getOptionalEntity(String value) {
        return Optional.of(getEntityFromMapOrFromDb(value));
    }

    @Override
    public List<E> getEntityList(List<String> values) {
        return values.stream()
                .map(this::getEntityFromMapOrFromDb)
                .toList();
    }

    @Override
    public void putEntity(String key, E entity) {
        entityMap.put(key, entity);
    }

    @Override
    public void updateEntity(String oldKey, String key, E entity) {
        entityMap.remove(oldKey);
        entityMap.put(key, entity);
    }

    @Override
    public void removeEntity(String key) {
        entityMap.remove(key);
    }

    @SuppressWarnings("unchecked")
    private E getEntityFromMapOrFromDb(String value) {
        parameter.setName(value);
        return entityMap.getOrDefault(value,
                (E) entityDao.getOptionalEntity(parameter)
                        .orElseThrow(RuntimeException::new));
    }
}
