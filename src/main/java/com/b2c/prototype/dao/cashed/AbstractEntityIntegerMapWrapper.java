package com.b2c.prototype.dao.cashed;

import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractEntityIntegerMapWrapper<E> implements IEntityIntegerMapWrapper<E> {

    protected final ISingleEntityDao entityDao;
    protected final Map<Integer, E> entityMap;
    protected final Parameter parameter;

    public AbstractEntityIntegerMapWrapper(ISingleEntityDao entityDao,
                                           Map<Integer, E> entityMap,
                                           String key) {
        this.entityDao = entityDao;
        this.entityMap = entityMap;
        this.parameter = new Parameter(key, null);
    }

    @Override
    public E getEntity(Integer value) {
        return getEntityFromMapOrFromDb(value);
    }

    @Override
    public Optional<E> getOptionalEntity(Integer value) {
        return Optional.of(getEntityFromMapOrFromDb(value));
    }

    @Override
    public List<E> getEntityList(List<Integer> values) {
        return values.stream()
                .map(this::getEntityFromMapOrFromDb)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private E getEntityFromMapOrFromDb(Integer value) {
        String valuesStr = value.toString();
        parameter.setName(valuesStr);
        return entityMap.getOrDefault(value,
                (E) entityDao.getOptionalEntity(parameter)
                        .orElseThrow(RuntimeException::new));
    }
}
