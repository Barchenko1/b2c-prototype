package com.b2c.prototype.service.query;

import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SearchService implements ISearchService {

    private final IFetchHandler fetchHandler;

    public SearchService(IFetchHandler fetchHandler) {
        this.fetchHandler = fetchHandler;
    }

//    @Override
//    public <E> E getGraphEntity(Class<E> clazz, String graph, Parameter parameter) {
//        return fetchHandler.getGraphEntity(clazz, graph, parameter);
//    }

    @Override
    public <E> E getNamedQueryEntity(Class<E> clazz, String namedQuery, Parameter parameter) {
        return fetchHandler.getNamedQueryEntity(clazz, namedQuery, parameter);
    }

//    @Override
//    public <E> Optional<E> getGraphOptionalEntity(Class<E> clazz, String graph, Parameter parameter) {
//        return fetchHandler.getGraphOptionalEntity(clazz, graph, parameter);
//    }

//    @Override
//    public <E> Optional<E> getNamedQueryOptionalEntity(Class<E> clazz, String namedQuery, Parameter parameter) {
//        return fetchHandler.getNamedQueryOptionalEntity(clazz, namedQuery, parameter);
//    }

//    @Override
//    public <E> List<E> getNamedQueryEntityList(Class<E> clazz, String namedQuery, Parameter parameter) {
//        if (parameter != null) {
//            return fetchHandler.getNamedQueryEntityList(clazz, namedQuery, parameter);
//        }
//        return fetchHandler.getNamedQueryEntityList(clazz, namedQuery);
//    }

//    @Override
//    public <R, E> R getGraphEntityDto(Class<E> clazz, String graphName, Parameter parameter, Function<E, R> mapToDtoFunction) {
//        E entity = fetchHandler.getGraphEntity(clazz, graphName, parameter);
//        return Optional.ofNullable(entity)
//                .map(mapToDtoFunction)
//                .orElse(null);
//    }

//    @Override
//    public <R, E> Optional<R> getGraphOptionalEntityDto(Class<E> clazz, String graphName, Parameter parameter, Function<E, R> mapToDtoFunction) {
//        E entity = fetchHandler.getGraphEntity(clazz, graphName, parameter);
//        return Optional.ofNullable(entity).map(mapToDtoFunction);
//    }

//    @Override
//    public <R, E> List<R> getGraphEntityDtoList(Class<E> clazz, String graphName, Function<E, R> mapToDtoFunction) {
//        List<E> entityList = fetchHandler.getGraphEntityList(clazz, graphName);
//        return entityList.stream()
//                .map(mapToDtoFunction)
//                .toList();
//    }

//    @Override
//    public <R, E> List<R> getSubGraphEntityDtoList(Class<E> clazz, String graphName, Parameter parameter, Function<E, R> mapToDtoFunction) {
//        List<E> subList = fetchHandler.getGraphEntityList(clazz, graphName, parameter);
//        return subList.stream()
//                .map(mapToDtoFunction)
//                .toList();
//    }

//    @Override
//    public <R, E> R getNamedQueryEntityDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction) {
//        E entity = fetchHandler.getNamedQueryEntity(clazz, namedQuery, parameter);
//        return Optional.ofNullable(entity)
//                .map(mapToDtoFunction)
//                .orElse(null);
//    }

//    @Override
//    public <R, E> List<R> getNamedQueryEntityDtoList(Class<E> clazz, String namedQuery, Function<Collection<E>, Collection<R>> mapToDtoFunction) {
//        List<E> entityList = fetchHandler.getNamedQueryEntityList(clazz, namedQuery);
//        return (List<R>) mapToDtoFunction.apply(entityList);
//    }
//
//    @Override
//    public <R, E> R getNamedQueryEntityDtoList(Class<E> clazz, String namedQuery, Parameter parameter, Function<Collection<E>, R> mapToDtoFunction) {
//        List<E> entityList = fetchHandler.getNamedQueryEntityList(clazz, namedQuery, parameter);
//        return mapToDtoFunction.apply(entityList);
//    }

//    @Override
//    public <R, E> Optional<R> getNamedQueryOptionalEntityDto(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction) {
//        E entity = fetchHandler.getNamedQueryEntity(clazz, namedQuery, parameter);
//        return Optional.ofNullable(entity)
//                .map(mapToDtoFunction);
//    }

//    @Override
//    public <R, E> List<R> getNamedQueryEntityDtoList(Class<E> clazz, String namedQuery, Function<E, R> mapToDtoFunction) {
//        List<E> entityList = fetchHandler.getNamedQueryEntityList(clazz, namedQuery);
//        return entityList.stream()
//                .map(mapToDtoFunction)
//                .toList();
//    }

//    @Override
//    public <R, E> List<R> getSubNamedQueryEntityDtoList(Class<E> clazz, String namedQuery, Parameter parameter, Function<E, R> mapToDtoFunction) {
//        List<E> subList = fetchHandler.getNamedQueryEntityList(clazz, namedQuery, parameter);
//        return subList.stream()
//                .map(mapToDtoFunction)
//                .toList();
//    }
}
