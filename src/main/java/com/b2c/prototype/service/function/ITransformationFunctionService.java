package com.b2c.prototype.service.function;

import java.util.function.Function;

public interface ITransformationFunctionService {
    <E, R> R getEntity(Class<R> classTo, E dataEntity);
    <E, R> R getEntity(Class<R> classTo, E dataEntity, String sol);
    <E, R> Function<E, R> getTransformationFunction(Class<E> classFrom, Class<R> classTo);
    <E, R> Function<E, R> getTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol);

    <E, R> void addOneFieldEntityDtoTransformationFunction(Class<R> classTo, Function<E, R> function);
    <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, Function<?, ?> function);
    <E, R> void addOneFieldEntityDtoTransformationFunction(Class<R> classTo, String sol, Function<E, R> function);
    <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol, Function<?, ?> function);

}
