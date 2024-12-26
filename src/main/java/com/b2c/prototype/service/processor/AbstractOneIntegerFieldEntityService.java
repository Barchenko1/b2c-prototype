package com.b2c.prototype.service.processor;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDtoUpdate;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractOneIntegerFieldEntityService<E> implements IOneIntegerFieldEntityService<E> {

    private final IParameterFactory parameterFactory;
    private final IEntityDao dao;
    protected final ITransformationFunctionService transformationFunctionService;
    private final ISingleValueMap singleValueMap;

    public AbstractOneIntegerFieldEntityService(IParameterFactory parameterFactory,
                                                IEntityDao dao,
                                                ITransformationFunctionService transformationFunctionService,
                                                ISingleValueMap singleValueMap) {
        this.parameterFactory = parameterFactory;
        this.dao = dao;
        this.transformationFunctionService = transformationFunctionService;
        this.singleValueMap = singleValueMap;
    }

    protected abstract Function<OneIntegerFieldEntityDto, E> getFunction();
    protected abstract String getFieldName();

    @Override
    public void saveEntity(OneIntegerFieldEntityDto oneIntegerFieldEntityDto) {
        E entity = getFunction().apply(oneIntegerFieldEntityDto);
        dao.persistEntity(entity);
        singleValueMap.putEntity(entity.getClass(), getFieldName(), entity);
    }

    @Override
    public void updateEntity(OneIntegerFieldEntityDtoUpdate oneFieldEntityDtoUpdate) {
        OneIntegerFieldEntityDto newEntityRequest = oneFieldEntityDtoUpdate.getNewEntity();
        E entity = getFunction().apply(newEntityRequest);
        Integer searchParameter = oneFieldEntityDtoUpdate.getOldEntity().getValue();
        Parameter parameter = parameterFactory.createIntegerParameter(getFieldName(), searchParameter);
        dao.findEntityAndUpdate(entity, parameter);
        singleValueMap.putRemoveEntity(
                entity.getClass(),
                searchParameter,
                newEntityRequest.getValue(),
                entity);
    }

    @Override
    public void deleteEntity(OneIntegerFieldEntityDto oneIntegerFieldEntityDto) {
        E entity = getFunction().apply(oneIntegerFieldEntityDto);
        dao.deleteEntity(entity);
        singleValueMap.removeEntity(entity.getClass(), oneIntegerFieldEntityDto.getValue());
    }

    @Override
    public E getEntity(OneIntegerFieldEntityDto oneIntegerFieldEntityDto) {
        Parameter parameter = parameterFactory
                .createIntegerParameter(getFieldName(), oneIntegerFieldEntityDto.getValue());
        return dao.getEntity(parameter);
    }

    @Override
    public Optional<E> getEntityOptional(OneIntegerFieldEntityDto oneIntegerFieldEntityDto) {
        Parameter parameter = parameterFactory
                .createIntegerParameter(getFieldName(), oneIntegerFieldEntityDto.getValue());
        return dao.getOptionalEntity(parameter);
    }

    @Override
    public List<E> getEntities() {
        return dao.getEntityList();
    }
}
