package com.b2c.prototype.service.common;

import com.tm.core.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IEntityQuery {
    <E> E getEntity(Parameter parameter);
    <E> Optional<E> getOptionalEntity(Parameter parameter);
    <E> List<E> getEntityList();

    <E> E getEntityGraph(String graph, Parameter parameter);
    <E> Optional<E> getOptionalEntityGraph(String graph, Parameter parameter);
    <E> List<E> getEntityGraphList(String graph);

    <E> E getEntityNamedQuery(String namedQuery, Parameter parameter);
    <E> Optional<E> getOptionalEntityNamedQuery(String namedQuery, Parameter parameter);
    <E> List<E> getEntityNamedQueryList(String namedQuery);

    <E, R> R getEntityGraphDto(String graph, Parameter parameter, Function<E, R> mapToDtoFunction);
    <E, R> Optional<R> getOptionalEntityGraphDto(String graph, Parameter parameter, Function<E, R> mapToDtoFunction);
    <E, R> List<R> getEntityGraphDtoList(String graph, Function<E, R> mapToDtoFunction);
    <E, R> List<R> getSubEntityGraphDtoList(String graph, Parameter parameter, Function<E, R> mapToDtoFunction);
}
