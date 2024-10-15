package com.b2c.prototype.service.transitive;

import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.util.TransitiveSelfEnum;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITransitiveSelfEntityService {

    <E extends TransitiveSelfEntity> void saveEntityTree(E entity);
    <E extends TransitiveSelfEntity> void updateEntityTree(E entity, Parameter... parameters);
    <E extends TransitiveSelfEntity> void deleteEntityTree(Parameter... parameters);

    <E extends TransitiveSelfEntity> List<E> getTransitiveSelfEntityList(Parameter... parameters);
    <E extends TransitiveSelfEntity> E getTransitiveSelfEntity(Parameter... parameters);
    <E extends TransitiveSelfEntity> Optional<E> getOptionalTransitiveSelfEntity(Parameter... parameters);

    <E extends TransitiveSelfEntity> Map<TransitiveSelfEnum, List<E>> getTransitiveSelfEntitiesTreeBySQLQuery(String sqlQuery);
}