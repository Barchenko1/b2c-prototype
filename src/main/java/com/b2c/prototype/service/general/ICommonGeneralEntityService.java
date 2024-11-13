package com.b2c.prototype.service.general;

import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface ICommonGeneralEntityService {
    void saveEntity(GeneralEntity generalEntity);
    void updateEntity(Consumer<Session> consumer);

    void deleteEntity(Class<?> clazz, Parameter... parameters);
    void deleteEntity(Parameter... parameters);

    <E> List<E> getEntityList(Class<?> clazz, Parameter... parameters);
    <E> E getEntity(Class<?> clazz, Parameter... parameters);
    <E> Optional<E> getOptionalEntity(Class<?> clazz, Parameter... parameters);
}
