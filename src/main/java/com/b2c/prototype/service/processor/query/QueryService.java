package com.b2c.prototype.service.processor.query;

import com.tm.core.dao.query.ISearchWrapper;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class QueryService implements IQueryService {

    private final ISearchWrapper searchWrapper;

    public QueryService(ISearchWrapper searchWrapper) {
        this.searchWrapper = searchWrapper;
    }

    @Override
    public <E> E getEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier) {
        return searchWrapper.getEntity(clazz, parameterSupplier.get());
    }

    @Override
    public <E> E getEntity(Class<?> clazz, String graphNamedQuery, Supplier<Parameter> parameterSupplier) {
        return searchWrapper.getEntityNamedQuery(clazz, graphNamedQuery, parameterSupplier.get());
    }

    @Override
    public <E> Optional<E> getOptionalEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier) {
        return Optional.ofNullable(searchWrapper.getEntity(clazz, parameterSupplier.get()));
    }

    @Override
    public <E> List<E> getEntityList(Class<?> clazz) {
        return searchWrapper.getEntityList(clazz);
    }

    @Override
    public <E> List<E> getEntityList(Class<?> clazz, String graphNamedQuery, Supplier<Parameter> parameterSupplier) {
        return searchWrapper.getEntityNamedQueryList(clazz, graphNamedQuery, parameterSupplier.get());
    }

    @Override
    public <E> List<E> getSubEntityList(Class<?> clazz, Supplier<Parameter> parameterSupplier) {
        return searchWrapper.getEntityList(clazz, parameterSupplier.get());
    }

    @Override
    public <R, E> R getEntityDto(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchWrapper.getEntity(clazz, parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> Optional<R> getOptionalEntityDto(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchWrapper.getEntity(clazz, parameterSupplier.get());
        return Optional.ofNullable(entity).map(mapToDtoFunction);
    }

    @Override
    public <R, E> List<R> getEntityDtoList(Class<?> clazz, Function<E, R> mapToDtoFunction) {
        List<E> entityList = searchWrapper.getEntityList(clazz);
        return entityList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> List<R> getSubEntityDtoList(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        List<E> subList = searchWrapper.getEntityList(clazz, parameterSupplier.get());
        return subList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> R getEntityGraphDto(Class<?> clazz, String graphName, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchWrapper.getEntityGraph(clazz, graphName, parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> Optional<R> getOptionalEntityGraphDto(Class<?> clazz, String graphName, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchWrapper.getEntityGraph(clazz, graphName, parameterSupplier.get());
        return Optional.ofNullable(entity).map(mapToDtoFunction);
    }

    @Override
    public <R, E> List<R> getEntityGraphDtoList(Class<?> clazz, String graphName, Function<E, R> mapToDtoFunction) {
        List<E> entityList = searchWrapper.getEntityListGraph(clazz, graphName);
        return entityList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> List<R> getSubEntityGraphDtoList(Class<?> clazz, String graphName, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        List<E> subList = searchWrapper.getEntityListGraph(clazz, graphName, parameterSupplier.get());
        return subList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public <R, E> R getEntityNamedQueryDto(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchWrapper.getEntityNamedQuery(clazz, namedQuery, parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> List<R> getEntityListNamedQueryDtoList(Class<?> clazz, String namedQuery, Function<Collection<E>, Collection<R>> mapToDtoFunction) {
        List<E> entityList = searchWrapper.getEntityNamedQueryList(clazz, namedQuery);
        return (List<R>) mapToDtoFunction.apply(entityList);
    }

    @Override
    public <R, E> R getEntityListNamedQueryDto(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<Collection<E>, R> mapToDtoFunction) {
        List<E> entityList = searchWrapper.getEntityNamedQueryList(clazz, namedQuery, parameterSupplier.get());
        return mapToDtoFunction.apply(entityList);
    }

    @Override
    public <R, E> Optional<R> getOptionalEntityNamedQueryDto(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = searchWrapper.getEntityNamedQuery(clazz, namedQuery, parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction);
    }

    @Override
    public <R, E> List<R> getSubEntityNamedQueryDtoList(Class<?> clazz, String namedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        List<E> subList = searchWrapper.getEntityNamedQueryList(clazz, namedQuery, parameterSupplier.get());
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

    @Override
    public <E> E getQueryEntityParameterArray(Query<E> query, Supplier<Parameter[]> parameterSupplier) {
        Parameter[] parameters = parameterSupplier.get();
        for (Parameter parameter : parameters) {
            query.setParameter(parameter.getName(), parameter.getValue());
        }
        return query.uniqueResult();
    }
}
