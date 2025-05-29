package com.b2c.prototype.transform.function;

import org.hibernate.Session;

import java.util.Collection;
import java.util.function.BiFunction;

public interface IBiTransformationFunction {

    <E, R> R getEntity(Session session, Class<R> classTo, E dataEntity);
    <E, R> R getEntity(Session session, Class<R> classTo, E dataEntity, String sol);

    <E, R> BiFunction<Session, E, Collection<R>> getTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> BiFunction<Session, E, Collection<R>> getTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo, String sol);

    <E, R> BiFunction<Session, E, R> getTransformationBiFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> BiFunction<Session, E, R> getTransformationBiFunction(Class<E> classFrom, Class<R> classTo, String sol);

    <E, R> BiFunction<Session, Collection<E>, R> getCollectionTransformationBiFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> BiFunction<Session, Collection<E>, R> getCollectionTransformationBiFunction(Class<E> classFrom, Class<R> classTo, String sol);

    <E, R> BiFunction<Session, Collection<E>, Collection<R>> getCollectionTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> BiFunction<Session, Collection<E>, Collection<R>> getCollectionTransformationCollectionBiFunction(Class<E> classFrom, Class<R> classTo, String sol);

    <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, BiFunction<Session, ?, ?> function);
    <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol, BiFunction<Session, ?, ?> function);

}
