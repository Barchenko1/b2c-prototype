package com.b2c.prototype.service.function;

import java.util.function.Function;

public interface ITransformationFunctionService {
    <E, R> Function<?, ?> getTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol);
    <E, R> R getEntity(Class<R> classTo, E dataEntity, String sol);
}
