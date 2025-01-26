package com.b2c.prototype.service.function;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TransformationFunctionService implements ITransformationFunctionService {

    private final Map<String, Function<?, ?>> functionMap;

    public TransformationFunctionService() {
        this.functionMap = new HashMap<>();
    }

    @Override
    public <E, R> Function<E, R> getTransformationFunction(Class<E> classFrom, Class<R> classTo) {
        return mapFunction(classFrom, classTo, null);
    }

    @Override
    public <E, R> R getEntity(Class<R> classTo, E dataEntity) {
        @SuppressWarnings("unchecked")
        Class<E> classFrom = (Class<E>) dataEntity.getClass();
        return mapFunction(classFrom, classTo, null).apply(dataEntity);
    }

    @Override
    public <E, R> Function<E, R> getTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return mapFunction(classFrom, classTo, sol);
    }

    @Override
    public <E, R> Function<Collection<E>, R> getCollectionTransformationFunction(Class<E> classFrom, Class<R> classTo) {
        return mapCollectionFunction(classFrom, classTo, null);
    }

    @Override
    public <E, R> Function<Collection<E>, R> getCollectionTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return mapCollectionFunction(classFrom, classTo, sol);
    }

    @Override
    public <E, R> Function<Collection<E>, Collection<R>> getCollectionTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo) {
        return mapCollectionToCollectionFunction(classFrom, classTo, null);
    }

    @Override
    public <E, R> Function<Collection<E>, Collection<R>> getCollectionTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return mapCollectionToCollectionFunction(classFrom, classTo, sol);
    }

    @Override
    public <E, R> Function<E, Collection<R>> getTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo) {
        return mapToCollectionFunction(classFrom, classTo, null);
    }

    @Override
    public <E, R> Function<E, Collection<R>> getTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return mapToCollectionFunction(classFrom, classTo, sol);
    }

    @Override
    public <E, R> R getEntity(Class<R> classTo, E dataEntity, String sol) {
        @SuppressWarnings("unchecked")
        Class<E> classFrom = (Class<E>) dataEntity.getClass();
        return mapFunction(classFrom, classTo, sol).apply(dataEntity);
    }

    @Override
    public <E, R> Collection<R> getEntityCollection(Class<R> classTo, E dataEntity) {
        @SuppressWarnings("unchecked")
        Class<E> classFrom = (Class<E>) dataEntity.getClass();
        return mapToCollectionFunction(classFrom, classTo, null).apply(dataEntity);
    }

    @Override
    public <E, R> Collection<R> getEntityCollection(Class<R> classTo, E dataEntity, String sol) {
        @SuppressWarnings("unchecked")
        Class<E> classFrom = (Class<E>) dataEntity.getClass();
        return mapToCollectionFunction(classFrom, classTo, null).apply(dataEntity);
    }

    @Override
    public <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, Function<?, ?> function) {
        this.functionMap.put(createKey(classFrom, classTo, null), function);
    }

    @Override
    public <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol, Function<?, ?> function) {
        this.functionMap.put(createKey(classFrom, classTo, sol), function);
    }

    private <E, R> String createKey(Class<E> classFrom, Class<R> classTo, String sol) {
        return sol != null && !sol.isEmpty()
                ? classFrom.getSimpleName() + " -> " + classTo.getSimpleName() + "[" + sol+ "]"
                : classFrom.getSimpleName() + " -> " + classTo.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    private <E, R> Function<E, R> mapFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (Function<E, R>) this.functionMap.get(createKey(classFrom, classTo, sol));
    }

    @SuppressWarnings("unchecked")
    private <E, R> Function<Collection<E>, R> mapCollectionFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (Function<Collection<E>, R>) this.functionMap.get(createKey(classFrom, classTo, sol));
    }

    @SuppressWarnings("unchecked")
    private <E, R> Function<E, Collection<R>> mapToCollectionFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (Function<E, Collection<R>>) this.functionMap.get(createKey(classFrom, classTo, sol));
    }

    @SuppressWarnings("unchecked")
    private <E, R> Function<Collection<E>, Collection<R>> mapCollectionToCollectionFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (Function<Collection<E>, Collection<R>>) this.functionMap.get(createKey(classFrom, classTo, sol));
    }

}
