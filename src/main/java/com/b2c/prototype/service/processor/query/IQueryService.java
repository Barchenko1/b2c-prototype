package com.b2c.prototype.service.processor.query;

import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IQueryService {
    <E> E getEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier);
    <E> Optional<E> getOptionalEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier);
    <E> List<E> getEntityList(Class<?> clazz);
    <E> List<E> getSubEntityList(Class<?> clazz, Supplier<Parameter> parameterSupplier);

    <R, E> R getEntityDto(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> Optional<R> getOptionalEntityDto(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getEntityDtoList(Class<?> clazz, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubEntityDtoList(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);

    <E> E getQueryEntity(Query<E> query, Supplier<Parameter> parameterSupplier);
    <E> E getQueryEntityParameterArray(Query<E> query, Supplier<Parameter[]> parameterSupplier);

}
