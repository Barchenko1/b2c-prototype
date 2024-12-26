package com.b2c.prototype.service.supplier;

import com.tm.core.processor.finder.parameter.Parameter;

import java.util.function.Function;
import java.util.function.Supplier;

public interface ISupplierService {
    <E, R> Supplier<R> entityFieldSupplier(Class<E> entityClass,
                                           String fieldName,
                                           String fieldValue,
                                           Function<E, R> fieldExtractor);
    <E, R> Supplier<R> entityFieldSupplier(Class<E> entityClass,
                                           Supplier<Parameter> parameterSupplier,
                                           Function<E, R> fieldExtractor);
    <E> Supplier<E> entityFieldSupplier(Class<E> entityClass,
                                        Supplier<Parameter> parameterSupplier);
    <E, R> Supplier<R> getSupplier(Class<R> classTo, E dataEntity);
    <E, R> Supplier<R> getSupplier(Class<R> classTo, E dataEntity, String sol);
    Supplier<Parameter> parameterStringSupplier(String key, String value);
}
