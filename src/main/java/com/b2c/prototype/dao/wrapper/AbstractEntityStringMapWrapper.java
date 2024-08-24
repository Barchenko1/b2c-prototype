package com.b2c.prototype.dao.wrapper;

import com.tm.core.dao.single.ISingleEntityDao;

import java.util.List;
import java.util.Map;

public abstract class AbstractEntityStringMapWrapper<E> implements IEntityStringMapWrapper<E> {

    protected final ISingleEntityDao entityDao;
    protected final Map<String, E> entityMap;
    protected final String query;

    public AbstractEntityStringMapWrapper(ISingleEntityDao entityDao,
                                          Map<String, E> entityMap,
                                          String query) {
        this.entityDao = entityDao;
        this.entityMap = entityMap;
        this.query = query;
    }

    @Override
    public E getEntity(String value) {
        return getEntityFromMapOrFromDb(value);
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

    private E getEntityFromMapOrFromDb(String value) {
        return entityMap.getOrDefault(value,
                (E) entityDao.getOptionalEntityBySQLQueryWithParams(query, value)
                        .orElseThrow(RuntimeException::new));
    }
}
