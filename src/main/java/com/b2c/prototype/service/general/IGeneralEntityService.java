package com.b2c.prototype.service.general;

import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IGeneralEntityService {
    void saveEntity(GeneralEntity generalEntity);
    <E> void updateEntity(Supplier<E> supplier);
    void updateEntity(Consumer<Session> consumer);

    <E> void deleteEntity(Class<?> clazz, Parameter... parameters);
    <E> void deleteEntity(Parameter... parameters);

    <E> List<E> getEntityList(Class<?> clazz, Parameter... parameters);
    <E> E getEntity(Class<?> clazz, Parameter... parameters);
    <E> Optional<E> getOptionalEntity(Class<?> clazz, Parameter... parameters);
}
