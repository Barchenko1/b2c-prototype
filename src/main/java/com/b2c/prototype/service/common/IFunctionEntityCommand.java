package com.b2c.prototype.service.common;

import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IFunctionEntityCommand {
    <E> void saveEntity(Supplier<E> entitySupplier);
    <E> void updateEntity(Supplier<E> entitySupplier);
    <E> void deleteEntity(Supplier<E> entitySupplier);

    void executeConsumer(Consumer<Session> consumer);

    <E> void updateEntityByParameter(Supplier<E> entitySupplier, Supplier<Parameter> parameterSupplier);
    void deleteEntityByParameter(Supplier<Parameter> parameterSupplier);
}
