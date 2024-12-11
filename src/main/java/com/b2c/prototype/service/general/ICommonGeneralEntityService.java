package com.b2c.prototype.service.general;

import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ICommonGeneralEntityService {
    void saveEntity(Consumer<Session> consumer);
    void updateEntity(Consumer<Session> consumer);
    void deleteEntity(Supplier<Parameter> parameterSupplier);
    <R, E> List<R> getEntityList(Function<E, R> mapToDtoFunction);
    <R, E> List<R> getSubEntityList(Supplier<Parameter[]> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> R getEntity(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
    <R, E> Optional<R> getOptionalEntity(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction);
}
