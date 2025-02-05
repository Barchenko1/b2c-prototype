package com.b2c.prototype.service.common;

import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IEntityQuery {
    <E> E getEntity(Supplier<Parameter> parameterSupplier);
    <E> Optional<E> getOptionalEntity(Supplier<Parameter> parameterSupplier);
    <E> List<E> getEntityList();
    <E> List<E> getSubEntityList(Supplier<Parameter[]> parameterSupplier);

    <E, R> R getEntityGraphDto(String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <E, R> Optional<R> getOptionalEntityGraphDto(String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <E, R> List<R> getEntityGraphDtoList(String graph, Function<E, R> mapToDtoFunction);
    <E, R> List<R> getSubEntityGraphDtoList(String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
}
