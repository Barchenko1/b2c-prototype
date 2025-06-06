package com.b2c.prototype.transform.function;

import org.hibernate.Session;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ITransformationFunction {
    <E, R> R getEntity(Class<R> classTo, E dataEntity);
    <E, R> R getEntity(Class<R> classTo, E dataEntity, String sol);

    <E, R> Function<E, Collection<R>> getTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> Function<E, Collection<R>> getTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo, String sol);

    <E, R> Function<E, R> getTransformationFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> Function<E, R> getTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol);

    <E, R> Function<Collection<E>, R> getCollectionTransformationFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> Function<Collection<E>, R> getCollectionTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol);

    <E, R> Function<Collection<E>, Collection<R>> getCollectionTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> Function<Collection<E>, Collection<R>> getCollectionTransformationCollectionFunction(Class<E> classFrom, Class<R> classTo, String sol);

    <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, Function<?, ?> function);
    <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol, Function<?, ?> function);

    <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, BiFunction<Session, ?, ?> function);
    <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol, BiFunction<Session, ?, ?> function);

    <E, R> BiFunction<Session, Collection<E>, Collection<R>> getCollectionTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> BiFunction<Session, Collection<E>, Collection<R>> getCollectionTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo, String sol);

}
