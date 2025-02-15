package com.b2c.prototype.service.function;

import org.hibernate.Session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TransformationFunctionService implements ITransformationFunctionService {

    private final Map<String, Function<?, ?>> functionMap;
    private final Map<String, BiFunction<Session, ?, ?>> biFunctionMap;

    public TransformationFunctionService() {
        this.functionMap = new HashMap<>();
        this.biFunctionMap = new HashMap<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> Function<E, R> getTransformationFunction(Class<E> classFrom, Class<R> classTo) {
        return (Function<E, R>) this.functionMap.get(createKey(classFrom, classTo, null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> R getEntity(Class<R> classTo, E dataEntity) {
        Class<E> classFrom = (Class<E>) dataEntity.getClass();
        Function<E, R> function = (Function<E, R>) this.functionMap.get(createKey(classFrom, classTo, null));
        return function.apply(dataEntity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> Function<E, R> getTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (Function<E, R>) this.functionMap.get(createKey(classFrom, classTo, sol));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> Function<Collection<E>, R> getCollectionTransformationFunction(Class<E> classFrom, Class<R> classTo) {
        return (Function<Collection<E>, R>) this.functionMap.get(createKey(classFrom, classTo, null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> Function<Collection<E>, R> getCollectionTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (Function<Collection<E>, R>) this.functionMap.get(createKey(classFrom, classTo, sol));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> Function<Collection<E>, Collection<R>> getCollectionTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo) {
        return (Function<Collection<E>, Collection<R>>) this.functionMap.get(createKey(classFrom, classTo, null));

    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> Function<Collection<E>, Collection<R>> getCollectionTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (Function<Collection<E>, Collection<R>>) this.functionMap.get(createKey(classFrom, classTo, sol));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> Function<E, Collection<R>> getTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo) {
        return (Function<E, Collection<R>>) this.functionMap.get(createKey(classFrom, classTo, null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> Function<E, Collection<R>> getTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (Function<E, Collection<R>>) this.functionMap.get(createKey(classFrom, classTo, sol));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> R getEntity(Class<R> classTo, E dataEntity, String sol) {
        Class<E> classFrom = (Class<E>) dataEntity.getClass();
        Function<E, R> function = (Function<E, R>) this.functionMap.get(createKey(classFrom, classTo, sol));
        return function.apply(dataEntity);
    }

    @Override
    public <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, Function<?, ?> function) {
        this.functionMap.put(createKey(classFrom, classTo, null), function);
    }

    @Override
    public <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol, Function<?, ?> function) {
        this.functionMap.put(createKey(classFrom, classTo, sol), function);
    }

    @Override
    public <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, BiFunction<Session, ?, ?> function) {
        this.biFunctionMap.put(createKey(classFrom, classTo, null), function);
    }

    @Override
    public <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol, BiFunction<Session, ?, ?> function) {
        this.biFunctionMap.put(createKey(classFrom, classTo, sol), function);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> R getEntity(Session session, Class<R> classTo, E dataEntity) {
        Class<E> classFrom = (Class<E>) dataEntity.getClass();
        BiFunction<Session, E, R> function = (BiFunction<Session, E, R>) this.biFunctionMap.get(createKey(classFrom, classTo, null));
        return function.apply(session, dataEntity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> R getEntity(Session session, Class<R> classTo, E dataEntity, String sol) {
        Class<E> classFrom = (Class<E>) dataEntity.getClass();
        BiFunction<Session, E, R> function = (BiFunction<Session, E, R>) this.biFunctionMap.get(createKey(classFrom, classTo, sol));
        return function.apply(session, dataEntity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> BiFunction<Session, E, Collection<R>> getTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo) {
        return (BiFunction<Session, E, Collection<R>>) this.biFunctionMap.get(createKey(classFrom, classTo, null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> BiFunction<Session, E, Collection<R>> getTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (BiFunction<Session, E, Collection<R>>) this.biFunctionMap.get(createKey(classFrom, classTo, null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> BiFunction<Session, E, R> getTransformationBiFunction(Class<E> classFrom, Class<R> classTo) {
        return (BiFunction<Session, E, R>) this.biFunctionMap.get(createKey(classFrom, classTo, null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> BiFunction<Session, E, R> getTransformationBiFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (BiFunction<Session, E, R>) this.biFunctionMap.get(createKey(classFrom, classTo, sol));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> BiFunction<Session, Collection<E>, R> getCollectionTransformationBiFunction(Class<E> classFrom, Class<R> classTo) {
        return (BiFunction<Session, Collection<E>, R>) this.biFunctionMap.get(createKey(classFrom, classTo, null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> BiFunction<Session, Collection<E>, R> getCollectionTransformationBiFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (BiFunction<Session, Collection<E>, R>) this.biFunctionMap.get(createKey(classFrom, classTo, sol));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> BiFunction<Session, Collection<E>, Collection<R>> getCollectionTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo) {
        return (BiFunction<Session, Collection<E>, Collection<R>>) this.biFunctionMap.get(createKey(classFrom, classTo, null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, R> BiFunction<Session, Collection<E>, Collection<R>> getCollectionTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (BiFunction<Session, Collection<E>, Collection<R>>) this.biFunctionMap.get(createKey(classFrom, classTo, sol));
    }

    private <E, R> String createKey(Class<E> classFrom, Class<R> classTo, String sol) {
        return sol != null && !sol.isEmpty()
                ? classFrom.getSimpleName() + " -> " + classTo.getSimpleName() + "[" + sol+ "]"
                : classFrom.getSimpleName() + " -> " + classTo.getSimpleName();
    }

}
