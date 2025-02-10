package com.b2c.prototype.service.query;

import com.tm.core.dao.query.ISearchHandler;
import com.tm.core.finder.parameter.Parameter;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class QueryService implements IQueryService {

    private final ISearchHandler searchHandler;

    public QueryService(ISearchHandler searchHandler) {
        this.searchHandler = searchHandler;
    }

    @Override
    public <E> E getEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier) {
        return searchHandler.getEntity(clazz, parameterSupplier.get());
    }

    @Override
    public <E> E getGraphEntity(Class<?> clazz, String graph, Supplier<Parameter> parameterSupplier) {
        return searchHandler.getEntityGraph(clazz, graph, parameterSupplier.get());
    }

    @Override
    public <E> E getNamedQueryEntity(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier) {
        return searchHandler.getEntityNamedQuery(clazz, namedQuery, parameterSupplier.get());
    }

    @Override
    public <E> Optional<E> getOptionalEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier) {
        return Optional.ofNullable(searchHandler.getEntity(clazz, parameterSupplier.get()));
    }

    @Override
    public <E> Optional<E> getOptionalEntityGraph(Class<?> clazz, String graph, Supplier<Parameter> parameterSupplier) {
        return searchHandler.getOptionalEntityGraph(clazz, graph, parameterSupplier.get());
    }

    @Override
    public <E> Optional<E> getOptionalEntityNamedQuery(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier) {
        return searchHandler.getOptionalEntityNamedQuery(clazz, namedQuery, parameterSupplier.get());
    }

    @Override
    public <E> List<E> getEntityList(Class<?> clazz) {
        return searchHandler.getEntityList(clazz);
    }

    @Override
    public <E> List<E> getEntityListNamedQuery(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier) {
        return searchHandler.getEntityNamedQueryList(clazz, namedQuery, parameterSupplier.get());
    }

    @Override
    public <E> List<E> getSubEntityList(Class<?> clazz, Supplier<Parameter> parameterSupplier) {
        return searchHandler.getEntityList(clazz, parameterSupplier.get());
    }

    @Override
    public <R, E> R getEntityDto(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchHandler.getEntity(clazz, parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> List<R> getEntityDtoList(Class<?> clazz, Function<E, R> mapToDtoFunction) {
        List<E> entityList = searchHandler.getEntityList(clazz);
        return entityList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> List<R> getSubEntityDtoList(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        List<E> subList = searchHandler.getEntityList(clazz, parameterSupplier.get());
        return subList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> R getEntityGraphDto(Class<?> clazz, String graphName, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchHandler.getEntityGraph(clazz, graphName, parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> Optional<R> getOptionalEntityGraphDto(Class<?> clazz, String graphName, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchHandler.getEntityGraph(clazz, graphName, parameterSupplier.get());
        return Optional.ofNullable(entity).map(mapToDtoFunction);
    }

    @Override
    public <R, E> List<R> getEntityGraphDtoList(Class<?> clazz, String graphName, Function<E, R> mapToDtoFunction) {
        List<E> entityList = searchHandler.getEntityListGraph(clazz, graphName);
        return entityList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> List<R> getSubEntityGraphDtoList(Class<?> clazz, String graphName, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        List<E> subList = searchHandler.getEntityListGraph(clazz, graphName, parameterSupplier.get());
        return subList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> R getEntityNamedQueryDto(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchHandler.getEntityNamedQuery(clazz, namedQuery, parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> List<R> getEntityListNamedQueryDtoList(Class<?> clazz, String namedQuery, Function<Collection<E>, Collection<R>> mapToDtoFunction) {
        List<E> entityList = searchHandler.getEntityNamedQueryList(clazz, namedQuery);
        return (List<R>) mapToDtoFunction.apply(entityList);
    }

    @Override
    public <R, E> R getEntityListNamedQueryDto(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<Collection<E>, R> mapToDtoFunction) {
        List<E> entityList = searchHandler.getEntityNamedQueryList(clazz, namedQuery, parameterSupplier.get());
        return mapToDtoFunction.apply(entityList);
    }

    @Override
    public <R, E> Optional<R> getOptionalEntityNamedQueryDto(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchHandler.getEntityNamedQuery(clazz, namedQuery, parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction);
    }

    @Override
    public <R, E> List<R> getSubEntityNamedQueryDtoList(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        List<E> subList = searchHandler.getEntityNamedQueryList(clazz, namedQuery, parameterSupplier.get());
        return subList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    public  <E> E getQueryEntityTest(Query<E> query, Supplier<Parameter> parameterSupplier) {
        Parameter parameter = parameterSupplier.get();
        query.setParameter(parameter.getName(), parameter.getValue());
        return query.uniqueResult();
    }

    @Override
    public  <E> E getQueryEntity(Query<E> query, Supplier<Parameter> parameterSupplier) {
        Parameter parameter = parameterSupplier.get();
        query.setParameter(parameter.getName(), parameter.getValue());
        return query.uniqueResult();
    }
}
