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

    <R, E> R getEntityDto(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> Optional<R> getOptionalEntityDto(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> List<R> getEntityDtoList(Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubEntityDtoList(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
}
