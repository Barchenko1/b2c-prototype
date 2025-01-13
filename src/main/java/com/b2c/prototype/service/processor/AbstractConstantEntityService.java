package com.b2c.prototype.service.processor;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

public abstract class AbstractConstantEntityService<E> implements IConstantEntityService {

    private final IParameterFactory parameterFactory;
    private final IEntityDao dao;
    protected final ITransformationFunctionService transformationFunctionService;
    private final ISingleValueMap singleValueMap;

    public AbstractConstantEntityService(IParameterFactory parameterFactory,
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
    public void saveEntity(ConstantEntityPayloadDto constantEntityPayloadDto) {
        E entity = (E) transformationFunctionService.getEntity(dao.getEntityClass(), constantEntityPayloadDto);
        dao.persistEntity(entity);
        singleValueMap.putEntity(dao.getEntityClass(), VALUE, entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity(String searchValue, ConstantEntityPayloadDto constantEntityPayloadDto) {
        E entity = (E) transformationFunctionService.getEntity(dao.getEntityClass(), constantEntityPayloadDto);
        Parameter parameter = parameterFactory.createStringParameter(VALUE, searchValue);
        dao.findEntityAndUpdate(entity, parameter);
        singleValueMap.putRemoveEntity(
                entity.getClass(),
                searchValue,
                constantEntityPayloadDto.getValue(),
                entity);
    }

    @Override
    public void deleteEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        dao.findEntityAndDelete(parameter);
        singleValueMap.removeEntity(dao.getEntityClass(), value);
    }

    @Override
    public ConstantEntityPayloadDto getEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        E entity = dao.getEntity(parameter);
        return transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, entity);
    }

    @Override
    public Optional<ConstantEntityPayloadDto> getEntityOptional(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        E entity = dao.getEntity(parameter);
        return Optional.of(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ConstantEntityPayloadDto> getEntities() {
        return dao.getEntityList().stream()
                .map(e -> (E) e)
                .map(entity ->
                        transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, entity))
                .toList();
    }
}
