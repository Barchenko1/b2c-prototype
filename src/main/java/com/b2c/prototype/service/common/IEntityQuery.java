package com.b2c.prototype.service.common;

import com.tm.core.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IEntityQuery {
    <E> E getEntity(Supplier<Parameter> parameterSupplier);
    <E> Optional<E> getOptionalEntity(Supplier<Parameter> parameterSupplier);
    <E> List<E> getEntityList();

    <E> E getEntityGraph(String graph, Supplier<Parameter> parameterSupplier);
    <E> Optional<E> getOptionalEntityGraph(String graph, Supplier<Parameter> parameterSupplier);
    <E> List<E> getEntityGraphList(String graph);

    <E> E getEntityNamedQuery(String namedQuery, Supplier<Parameter> parameterSupplier);
    <E> Optional<E> getOptionalEntityNamedQuery(String namedQuery, Supplier<Parameter> parameterSupplier);
    <E> List<E> getEntityNamedQueryList(String namedQuery);

    <E, R> R getEntityGraphDto(String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <E, R> Optional<R> getOptionalEntityGraphDto(String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <E, R> List<R> getEntityGraphDtoList(String graph, Function<E, R> mapToDtoFunction);
    <E, R> List<R> getSubEntityGraphDtoList(String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
}
