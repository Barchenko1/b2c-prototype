package com.b2c.prototype.dao.cashed;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IEntityCachedMap {

    Map<Object, Object> getEntityMap(Class<?> clazz);
    <E> E getEntity(Class<?> clazz, String key, Object value);
    <E> Optional<E> getOptionalEntity(Class<?> clazz, String key, Object value);
    <E> List<E> getEntityList(Class<?> clazz, String key, List<Object> values);
    <E> void putEntity(Class<?> clazz, String key, E entity);
    <E> void updateEntity(Class<?> clazz, Object oldKey, Object key, E entity);
    void removeEntity(Class<?> clazz, Object key);

}
