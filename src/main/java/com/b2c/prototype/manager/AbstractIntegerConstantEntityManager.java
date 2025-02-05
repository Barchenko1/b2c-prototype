package com.b2c.prototype.manager;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.base.AbstractNumberConstantEntity;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.VALUE;

public abstract class AbstractIntegerConstantEntityManager<T, E extends AbstractNumberConstantEntity> implements IIntegerConstantEntityManager {

    private final IParameterFactory parameterFactory;
    private final IEntityDao dao;
    private final IConstantsScope singleValueMap;
    private final Function<T, E> mapDtoToEntityFunction;
    private final Function<E, T> mapEntityToDtoFunction;

    public AbstractIntegerConstantEntityManager(IParameterFactory parameterFactory,
                                                IEntityDao dao,
                                                IConstantsScope singleValueMap,
                                                Function<T, E> mapDtoToEntityFunction,
                                                Function<E, T> mapEntityToDtoFunction) {
        this.parameterFactory = parameterFactory;
        this.dao = dao;
        this.singleValueMap = singleValueMap;
        this.mapDtoToEntityFunction = mapDtoToEntityFunction;
        this.mapEntityToDtoFunction = mapEntityToDtoFunction;
    }

    @Override
    public void saveEntity(NumberConstantPayloadDto numberConstantPayloadDto) {
        E entity = mapDtoToEntityFunction.apply((T) numberConstantPayloadDto);
        dao.persistEntity(entity);
        singleValueMap.putEntity(dao.getEntityClass(), VALUE, entity);
    }

    @Override
    public void updateEntity(Integer searchValue, NumberConstantPayloadDto numberConstantPayloadDto) {
        E entity = mapDtoToEntityFunction.apply((T) numberConstantPayloadDto);
        Parameter parameter = parameterFactory.createIntegerParameter(VALUE, searchValue);
        dao.findEntityAndUpdate(entity, parameter);
        singleValueMap.putRemoveEntity(
                entity.getClass(),
                searchValue,
                entity.getValue(),
                entity);
    }

    @Override
    public void deleteEntity(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        dao.findEntityAndDelete(parameter);
        singleValueMap.removeEntity(dao.getEntityClass(), ratingValue);
    }

    @Override
    public NumberConstantPayloadDto getEntity(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        E entity = dao.getEntity(parameter);
        return (NumberConstantPayloadDto) mapEntityToDtoFunction.apply(entity);
    }

    @Override
    public Optional<NumberConstantPayloadDto> getEntityOptional(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        E entity = dao.getEntity(parameter);
        return Optional.of((NumberConstantPayloadDto) mapEntityToDtoFunction.apply(entity));
    }

    @Override
    public List<NumberConstantPayloadDto> getEntities() {
        return (List<NumberConstantPayloadDto>) dao.getEntityList().stream()
                .map(entity -> mapEntityToDtoFunction.apply((E) entity))
                .collect(Collectors.toList());
    }
}
