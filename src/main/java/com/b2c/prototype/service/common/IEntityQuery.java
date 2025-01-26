package com.b2c.prototype.service.common;

import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IEntityQuery {
    <E> E getEntity(Supplier<Parameter> parameterSupplier);
    <E> Optional<E> getOptionalEntity(Supplier<Parameter> parameterSupplier);
    <E> List<E> getEntityList();
    <E> List<E> getSubEntityList(Supplier<Parameter[]> parameterSupplier);

    <E, R> R getEntityDto(String graphNamedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <E, R> Optional<R> getOptionalEntityDto(String graphNamedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <E, R> List<R> getEntityDtoList(String graphNamedQuery, Function<E, R> mapToDtoFunction);
    <E, R> List<R> getSubEntityDtoList(String graphNamedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
}
