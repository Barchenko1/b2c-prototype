package com.b2c.prototype.service.query;

import com.tm.core.finder.parameter.Parameter;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ISearchService {
    <E> E getGraphEntity(Class<E> clazz, String graph, Parameter parameter);
    <E> E getNamedQueryEntity(Class<E> clazz, String namedQuery, Parameter parameter);
    <E> Optional<E> getGraphOptionalEntity(Class<E> clazz, String graph, Parameter parameter);
    <E> Optional<E> getNamedQueryOptionalEntity(Class<E> clazz, String namedQuery, Parameter parameter);
    <E> List<E> getNamedQueryEntityList(Class<E> clazz, String namedQuery, Parameter parameter);

    <R, E> R getGraphEntityDto(Class<E> clazz, String graph, Parameter parameter, Function<E, R> mapToDtoFunction);
    <R, E> Optional<R> getGraphOptionalEntityDto(Class<E> clazz, String graph, Parameter parameter, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getGraphEntityDtoList(Class<E> clazz, String graph, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubGraphEntityDtoList(Class<E> clazz, String graph, Parameter parameter, Function<E, R> mapToDtoFunction);

    <R, E> R getNamedQueryEntityDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getNamedQueryEntityDtoList(Class<E> clazz, String namedQuery, Function<Collection<E>, Collection<R>> mapToDtoFunction);
    <R, E> R getNamedQueryEntityDtoList(Class<E> clazz, String namedQuery, Parameter parameter, Function<Collection<E>, R> mapToDtoFunction);
    <R, E> Optional<R> getNamedQueryOptionalEntityDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubNamedQueryEntityDtoList(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction);

    <E> E getQueryEntity(Query<E> query, Supplier<Parameter> parameterSupplier);

}
