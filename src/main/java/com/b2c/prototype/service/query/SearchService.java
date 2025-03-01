package com.b2c.prototype.service.query;

import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.finder.parameter.Parameter;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class SearchService implements ISearchService {

    private final IFetchHandler fetchHandler;

    public SearchService(IFetchHandler fetchHandler) {
        this.fetchHandler = fetchHandler;
    }

    @Override
    public <E> E getEntity(Class<E> clazz, Supplier<Parameter> parameterSupplier) {
        return fetchHandler.getEntity(clazz, parameterSupplier.get());
    }

    @Override
    public <E> E getGraphEntity(Class<E> clazz, String graph, Parameter parameter) {
        return fetchHandler.getEntityGraph(clazz, graph, parameter);
    }

    @Override
    public <E> E getNamedQueryEntity(Class<E> clazz, String namedQuery, Parameter parameter) {
        return fetchHandler.getEntityNamedQuery(clazz, namedQuery, parameter);
    }

    @Override
    public <E> Optional<E> getOptionalEntity(Class<E> clazz, Parameter parameter) {
        return Optional.ofNullable(fetchHandler.getEntity(clazz, parameter));
    }

    @Override
    public <E> Optional<E> getOptionalEntityGraph(Class<E> clazz, String graph, Parameter parameter) {
        return fetchHandler.getOptionalEntityGraph(clazz, graph, parameter);
    }

    @Override
    public <E> Optional<E> getOptionalEntityNamedQuery(Class<E> clazz, String namedQuery, Parameter parameter) {
        return fetchHandler.getOptionalEntityNamedQuery(clazz, namedQuery, parameter);
    }

    @Override
    public <E> List<E> getEntityList(Class<E> clazz) {
        return fetchHandler.getEntityList(clazz);
    }

    @Override
    public <E> List<E> getEntityListNamedQuery(Class<E> clazz, String namedQuery, Parameter parameter) {
        return fetchHandler.getEntityNamedQueryList(clazz, namedQuery, parameter);
    }

    @Override
    public <E> List<E> getSubEntityList(Class<E> clazz, Parameter parameter) {
        return fetchHandler.getEntityList(clazz, parameter);
    }

    @Override
    public <R, E> R getEntityDto(Class<E> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = fetchHandler.getEntity(clazz, parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> List<R> getSubEntityDtoList(Class<E> clazz, Parameter parameter, Function<E, R> mapToDtoFunction) {
        List<E> subList = fetchHandler.getEntityList(clazz, parameter);
        return subList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> R getEntityGraphDto(Class<E> clazz, String graphName, Parameter parameter, Function<E, R> mapToDtoFunction) {
        E entity = fetchHandler.getEntityGraph(clazz, graphName, parameter);
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> Optional<R> getOptionalEntityGraphDto(Class<E> clazz, String graphName, Parameter parameter, Function<E, R> mapToDtoFunction) {
        E entity = fetchHandler.getEntityGraph(clazz, graphName, parameter);
        return Optional.ofNullable(entity).map(mapToDtoFunction);
    }

    @Override
    public <R, E> List<R> getEntityGraphDtoList(Class<E> clazz, String graphName, Function<E, R> mapToDtoFunction) {
        List<E> entityList = fetchHandler.getEntityListGraph(clazz, graphName);
        return entityList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> List<R> getSubEntityGraphDtoList(Class<E> clazz, String graphName, Parameter parameter, Function<E, R> mapToDtoFunction) {
        List<E> subList = fetchHandler.getEntityListGraph(clazz, graphName, parameter);
        return subList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> R getEntityNamedQueryDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction) {
        E entity = fetchHandler.getEntityNamedQuery(clazz, namedQuery, parameter);
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> List<R> getEntityListNamedQueryDtoList(Class<E> clazz, String namedQuery, Function<Collection<E>, Collection<R>> mapToDtoFunction) {
        List<E> entityList = fetchHandler.getEntityNamedQueryList(clazz, namedQuery);
        return (List<R>) mapToDtoFunction.apply(entityList);
    }

    @Override
    public <R, E> R getEntityListNamedQueryDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<Collection<E>, R> mapToDtoFunction) {
        List<E> entityList = fetchHandler.getEntityNamedQueryList(clazz, namedQuery, parameter);
        return mapToDtoFunction.apply(entityList);
    }

    @Override
    public <R, E> Optional<R> getOptionalEntityNamedQueryDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction) {
        E entity = fetchHandler.getEntityNamedQuery(clazz, namedQuery, parameter);
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction);
    }

    @Override
    public <R, E> List<R> getSubEntityNamedQueryDtoList(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction) {
        List<E> subList = fetchHandler.getEntityNamedQueryList(clazz, namedQuery, parameter);
        return subList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public  <E> E getQueryEntity(Query<E> query, Supplier<Parameter> parameterSupplier) {
        Parameter parameter = parameterSupplier.get();
        query.setParameter(parameter.getName(), parameter.getValue());
        return query.uniqueResult();
    }
}
