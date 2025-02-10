package com.b2c.prototype.service.scope;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IConstantsScope {

    Map<Object, Object> getEntityMap(Class<?> clazz);
    <E> E getEntity(Class<E> clazz, String key, Object value);
    boolean isEntityExist(Class<?> clazz, String key, Object value);
    <E> Optional<E> getOptionalEntity(Class<E> clazz, String key, Object value);
    <E> Optional<E> getOptionalEntityGraph(Class<E> clazz, String graph, String key, Object value);
    <E> Optional<E> getOptionalEntityNamedQuery(Class<E> clazz, String namedQuery, String key, Object value);
    <E> List<E> getEntityList(Class<E> clazz, String key, Object value);
    <E> List<E> getEntityList(Class<E> clazz, String key, List<Object> values);

    <E> void putEntity(Class<?> clazz, String key, E entity);
    <E> void putRemoveEntity(Class<?> clazz, Object oldKey, Object key, E entity);
    void removeEntity(Class<?> clazz, Object key);

}
