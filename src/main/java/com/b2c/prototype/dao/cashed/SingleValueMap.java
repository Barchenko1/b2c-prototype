package com.b2c.prototype.dao.cashed;

import com.tm.core.dao.query.ISearchWrapper;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.factory.ParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class SingleValueMap implements ISingleValueMap {

    private final Map<Class<?>, Map<Object, Object>> classEntityMap;
    private final ISearchWrapper searchWrapper;
    private final IParameterFactory parameterFactory;
    private final Map<Class<?>, BiFunction<String, Object, Parameter>> typeMap;

    public SingleValueMap(Map<Class<?>, Map<?, ?>> entityMap,
                          ISearchWrapper searchWrapper) {
        this.classEntityMap = castMap(entityMap);
        this.searchWrapper = searchWrapper;
        this.parameterFactory = new ParameterFactory();
        this.typeMap = new HashMap<>(){{
            put(Integer.class, (key, value) -> parameterFactory.createIntegerParameter(key, (Integer) value));
            put(Long.class, (key, value) -> parameterFactory.createLongParameter(key, (Long) value));
            put(Double.class, (key, value) -> parameterFactory.createDoubleParameter(key, (Double) value));
            put(Boolean.class, (key, value) -> parameterFactory.createBooleanParameter(key, (Boolean) value));
            put(String.class, (key, value) -> parameterFactory.createStringParameter(key, (String) value));
        }};
    }

    @Override
    public Map<Object, Object> getEntityMap(Class<?> clazz) {
        return classEntityMap.get(clazz);
    }

    @Override
    public <E> E getEntity(Class<?> clazz, String key, Object value) {
        return getEntityFromMapOrFromDb(clazz, key, value);
    }

    @Override
    public boolean isEntityExist(Class<?> clazz, String key, Object value) {
        return getIsEntityExistFromMapOrFromDb(clazz, key, value);
    }

    @Override
    public <E> Optional<E> getOptionalEntity(Class<?> clazz, String key, Object value) {
        return Optional.of(getEntityFromMapOrFromDb(clazz, key, value));
    }

    @Override
    public <E> List<E> getEntityList(Class<?> clazz, String key, Object value) {
        return getEntityListFromMapOrFromDb(clazz, key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> List<E> getEntityList(Class<?> clazz, String key, List<Object> values) {
        return values.stream()
                .map(value -> getEntityFromMapOrFromDb(clazz, key, value))
                .map(e -> (E) e)
                .toList();
    }

    @Override
    public <E> void putEntity(Class<?> clazz, String key, E entity) {
        Optional.ofNullable(classEntityMap.get(clazz))
                .ifPresent(entityMap -> entityMap.put(key, entity));
    }

    @Override
    public <E> void putRemoveEntity(Class<?> clazz, Object oldKey, Object key, E entity) {
        Optional.ofNullable(classEntityMap.get(clazz))
                .ifPresent(entityMap -> {
                    entityMap.remove(oldKey);
                    entityMap.put(key, entity);
                });
    }

    @Override
    public void removeEntity(Class<?> clazz, Object value) {
        Optional.ofNullable(classEntityMap.get(clazz)).ifPresent(map -> map.remove(value));
    }

    @SuppressWarnings("unchecked")
    private <E> E getEntityFromMapOrFromDb(Class<?> clazz, String key, Object value) {
        Map<Object, Object> entityMap = classEntityMap.get(clazz);
        return Optional.ofNullable((E) entityMap.get(value))
                .orElseGet(() -> {
                    E entityFromDb = (E) searchWrapper.getOptionalEntity(clazz, createParameter(key, value))
                            .orElseThrow(() -> new RuntimeException("Entity not found in cache or DB"));

                    Optional.of(entityMap).ifPresent(map -> map.put(value, entityFromDb));

                    return entityFromDb;
                });
    }

    @SuppressWarnings("unchecked")
    private <E> List<E> getEntityListFromMapOrFromDb(Class<?> clazz, String key, Object value) {
        Map<Object, Object> entityMap = classEntityMap.get(clazz);
        return (List<E>) Optional.ofNullable(entityMap.get(value))
                .orElseGet(() -> {
                    List<E> entityListFromDb = searchWrapper.getEntityList(clazz, createParameter(key, value));
                    entityListFromDb.forEach(entityFromDb -> {
                        Optional.of(entityMap).ifPresent(map -> map.put(value, entityFromDb));
                    });

                    return entityListFromDb;
                });
    }

    private boolean getIsEntityExistFromMapOrFromDb(Class<?> clazz, String key, Object value) {
        return classEntityMap.getOrDefault(clazz, Map.of()).containsKey(value) ||
                searchWrapper.getOptionalEntity(clazz, createParameter(key, value)).isPresent();
    }

    private Parameter createParameter(String key, Object value) {
        BiFunction<String, Object, Parameter> biFunction = typeMap.get(value.getClass());
        return biFunction.apply(key, value);
    }

    private Map<Class<?>, Map<Object, Object>> castMap(Map<Class<?>, Map<?, ?>> originalMap) {
        Map<Class<?>, Map<Object, Object>> castedMap = new HashMap<>();
        for (Map.Entry<Class<?>, Map<?, ?>> entry : originalMap.entrySet()) {
            Map<Object, Object> innerMap = new HashMap<>(entry.getValue());
            castedMap.put(entry.getKey(), innerMap);
        }
        return castedMap;
    }
}
