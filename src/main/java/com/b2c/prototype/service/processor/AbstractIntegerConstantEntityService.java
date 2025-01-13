package com.b2c.prototype.service.processor;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.ConstantNumberEntityPayloadDto;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.VALUE;

public abstract class AbstractIntegerConstantEntityService<E> implements IIntegerConstantEntityService {

    private final IParameterFactory parameterFactory;
    private final IEntityDao dao;
    protected final ITransformationFunctionService transformationFunctionService;
    private final ISingleValueMap singleValueMap;

    public AbstractIntegerConstantEntityService(IParameterFactory parameterFactory,
                                                IEntityDao dao,
                                                ITransformationFunctionService transformationFunctionService,
                                                ISingleValueMap singleValueMap) {
        this.parameterFactory = parameterFactory;
        this.dao = dao;
        this.transformationFunctionService = transformationFunctionService;
        this.singleValueMap = singleValueMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveEntity(ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto) {
        E entity = (E) transformationFunctionService.getEntity(dao.getEntityClass(), constantNumberEntityPayloadDto);
        dao.persistEntity(entity);
        singleValueMap.putEntity(dao.getEntityClass(), VALUE, entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(Integer searchValue, ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto) {
        E entity = (E) transformationFunctionService.getEntity(dao.getEntityClass(), constantNumberEntityPayloadDto);
        Parameter parameter = parameterFactory.createIntegerParameter(VALUE, searchValue);
        dao.findEntityAndUpdate(entity, parameter);
        singleValueMap.putRemoveEntity(
                entity.getClass(),
                searchValue,
                constantNumberEntityPayloadDto.getValue(),
                entity);
    }

    @Override
    public void deleteEntity(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        dao.findEntityAndDelete(parameter);
        singleValueMap.removeEntity(dao.getEntityClass(), ratingValue);
    }

    @Override
    public ConstantNumberEntityPayloadDto getEntity(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        E entity = dao.getEntity(parameter);
        return transformationFunctionService.getEntity(ConstantNumberEntityPayloadDto.class, entity);
    }

    @Override
    public Optional<ConstantNumberEntityPayloadDto> getEntityOptional(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        E entity = dao.getEntity(parameter);
        return Optional.of(transformationFunctionService.getEntity(ConstantNumberEntityPayloadDto.class, entity));
    }

    @Override
    public List<ConstantNumberEntityPayloadDto> getEntities() {
        return dao.getEntityList().stream()
                .map(entity -> transformationFunctionService.getEntity(ConstantNumberEntityPayloadDto.class, entity))
                .collect(Collectors.toList());
    }
}
