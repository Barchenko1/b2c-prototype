package com.b2c.prototype.service.query;

import com.tm.core.finder.parameter.Parameter;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IQueryService {
    <E> E getEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier);
    <E> E getGraphEntity(Class<?> clazz, String graph, Supplier<Parameter> parameterSupplier);
    <E> E getNamedQueryEntity(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier);
    <E> Optional<E> getOptionalEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier);
    <E> Optional<E> getOptionalEntityGraph(Class<?> clazz, String graph, Supplier<Parameter> parameterSupplier);
    <E> Optional<E> getOptionalEntityNamedQuery(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier);
    <E> List<E> getEntityList(Class<?> clazz);
    <E> List<E> getEntityListNamedQuery(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier);
    <E> List<E> getSubEntityList(Class<?> clazz, Supplier<Parameter> parameterSupplier);

    //replace soon
    <R, E> R getEntityDto(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getEntityDtoList(Class<?> clazz, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubEntityDtoList(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);

    <R, E> R getEntityGraphDto(Class<?> clazz, String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> Optional<R> getOptionalEntityGraphDto(Class<?> clazz, String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getEntityGraphDtoList(Class<?> clazz, String graph, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubEntityGraphDtoList(Class<?> clazz, String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);

    <R, E> R getEntityNamedQueryDto(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getEntityListNamedQueryDtoList(Class<?> clazz, String namedQuery, Function<Collection<E>, Collection<R>> mapToDtoFunction);
    <R, E> R getEntityListNamedQueryDto(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<Collection<E>, R> mapToDtoFunction);
    <R, E> Optional<R> getOptionalEntityNamedQueryDto(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubEntityNamedQueryDtoList(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);

    <E> E getQueryEntity(Query<E> query, Supplier<Parameter> parameterSupplier);

}
