package com.b2c.prototype.service.single;

import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;

public interface ICommonSingleEntityService {
    <E> void saveEntity(E entity);
    <E> void updateEntity(E entity, Parameter... parameters);
    <E> void deleteEntityByParameter(Parameter... parameters);

    <E> List<E> getEntityList(Parameter... parameters);
    <E> E getEntity(Parameter... parameters);
    <E> Optional<E> getOptionalEntity(Parameter... parameters);
}
