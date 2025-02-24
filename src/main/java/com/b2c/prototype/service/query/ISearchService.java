package com.b2c.prototype.service.query;

import com.tm.core.finder.parameter.Parameter;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ISearchService {
    <E> E getEntity(Class<E> clazz, Supplier<Parameter> parameterSupplier);
    <E> E getGraphEntity(Class<E> clazz, String graph, Parameter parameter);
    <E> E getNamedQueryEntity(Class<E> clazz, String namedQuery, Parameter parameter);
    <E> Optional<E> getOptionalEntity(Class<E> clazz, Parameter parameter);
    <E> Optional<E> getOptionalEntityGraph(Class<E> clazz, String graph, Parameter parameter);
    <E> Optional<E> getOptionalEntityNamedQuery(Class<E> clazz, String namedQuery, Parameter parameter);
    <E> List<E> getEntityList(Class<E> clazz);
    <E> List<E> getEntityListNamedQuery(Class<E> clazz, String namedQuery, Parameter parameter);
    <E> List<E> getSubEntityList(Class<E> clazz, Parameter parameter);

    //replace soon
    <R, E> R getEntityDto(Class<E> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubEntityDtoList(Class<E> clazz, Parameter parameter, Function<E, R> mapToDtoFunction);

    <R, E> R getEntityGraphDto(Class<E> clazz, String graph, Parameter parameter, Function<E, R> mapToDtoFunction);
    <R, E> Optional<R> getOptionalEntityGraphDto(Class<E> clazz, String graph, Parameter parameter, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getEntityGraphDtoList(Class<E> clazz, String graph, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubEntityGraphDtoList(Class<E> clazz, String graph, Parameter parameter, Function<E, R> mapToDtoFunction);

    <R, E> R getEntityNamedQueryDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getEntityListNamedQueryDtoList(Class<E> clazz, String namedQuery, Function<Collection<E>, Collection<R>> mapToDtoFunction);
    <R, E> R getEntityListNamedQueryDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<Collection<E>, R> mapToDtoFunction);
    <R, E> Optional<R> getOptionalEntityNamedQueryDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubEntityNamedQueryDtoList(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction);

    <E> E getQueryEntity(Query<E> query, Supplier<Parameter> parameterSupplier);

}
