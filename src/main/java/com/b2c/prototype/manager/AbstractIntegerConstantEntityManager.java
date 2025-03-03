package com.b2c.prototype.manager;


import com.b2c.prototype.modal.base.constant.AbstractNumberConstantEntity;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.VALUE;

public abstract class AbstractIntegerConstantEntityManager<T, E extends AbstractNumberConstantEntity> implements IIntegerConstantEntityManager {

    private final IParameterFactory parameterFactory;
    private final IEntityDao dao;
    private final String namedQuery;
    private final Function<T, E> mapDtoToEntityFunction;
    private final Function<E, T> mapEntityToDtoFunction;

    public AbstractIntegerConstantEntityManager(IParameterFactory parameterFactory,
                                                IEntityDao dao, String namedQuery,
                                                Function<T, E> mapDtoToEntityFunction,
                                                Function<E, T> mapEntityToDtoFunction) {
        this.parameterFactory = parameterFactory;
        this.dao = dao;
        this.namedQuery = namedQuery;
        this.mapDtoToEntityFunction = mapDtoToEntityFunction;
        this.mapEntityToDtoFunction = mapEntityToDtoFunction;
    }

    @Override
    public void saveEntity(NumberConstantPayloadDto numberConstantPayloadDto) {
        E entity = mapDtoToEntityFunction.apply((T) numberConstantPayloadDto);
        dao.persistEntity(entity);
    }

    @Override
    public void updateEntity(Integer searchValue, NumberConstantPayloadDto numberConstantPayloadDto) {
        E entity = mapDtoToEntityFunction.apply((T) numberConstantPayloadDto);
        Parameter parameter = parameterFactory.createIntegerParameter(VALUE, searchValue);
        dao.findEntityAndUpdate(entity, parameter);
    }

    @Override
    public void deleteEntity(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        dao.findEntityAndDelete(parameter);
    }

    @Override
    public NumberConstantPayloadDto getEntity(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        E entity = dao.getNamedQueryEntity(namedQuery, parameter);
        return (NumberConstantPayloadDto) mapEntityToDtoFunction.apply(entity);
    }

    @Override
    public Optional<NumberConstantPayloadDto> getEntityOptional(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        E entity = dao.getNamedQueryEntity(namedQuery, parameter);
        return Optional.of((NumberConstantPayloadDto) mapEntityToDtoFunction.apply(entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NumberConstantPayloadDto> getEntities() {
        return (List<NumberConstantPayloadDto>) dao.getNamedQueryEntityList(namedQuery).stream()
                .map(entity -> mapEntityToDtoFunction.apply((E) entity))
                .collect(Collectors.toList());
    }
}
