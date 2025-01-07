package com.b2c.prototype.service.processor;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.base.AbstractOneColumnEntity;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.dto.response.ResponseOneFieldEntityDto;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractOneFieldEntityService<E> implements IOneFieldEntityService<E> {

    private final IParameterFactory parameterFactory;
    private final IEntityDao dao;
    protected final ITransformationFunctionService transformationFunctionService;
    private final ISingleValueMap singleValueMap;

    public AbstractOneFieldEntityService(IParameterFactory parameterFactory,
                                         IEntityDao dao,
                                         ITransformationFunctionService transformationFunctionService,
                                         ISingleValueMap singleValueMap) {
        this.parameterFactory = parameterFactory;
        this.dao = dao;
        this.transformationFunctionService = transformationFunctionService;
        this.singleValueMap = singleValueMap;
    }

    protected abstract Function<OneFieldEntityDto, E> getFunction();
    protected abstract String getFieldName();

    @Override
    public void saveEntity(OneFieldEntityDto oneFieldEntityDto) {
        E entity = getFunction().apply(oneFieldEntityDto);
//        E entity = (E) transformationFunctionService.getEntity(AbstractOneColumnEntity.class, oneFieldEntityDto);
        dao.persistEntity(entity);
        singleValueMap.putEntity(entity.getClass(), getFieldName(), entity);
    }

    @Override
    public void updateEntity(OneFieldEntityDtoUpdate oneFieldEntityDtoUpdate) {
        OneFieldEntityDto newEntityRequest = oneFieldEntityDtoUpdate.getNewEntity();
        E entity = getFunction().apply(newEntityRequest);
//        E entity = (E) transformationFunctionService.getEntity(AbstractOneColumnEntity.class, newEntityRequest);
        String searchParameter = oneFieldEntityDtoUpdate.getOldEntity().getValue();
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), searchParameter);
        dao.findEntityAndUpdate(entity, parameter);
        singleValueMap.putRemoveEntity(
                entity.getClass(),
                searchParameter,
                newEntityRequest.getValue(),
                entity);
    }

    @Override
    public void deleteEntity(OneFieldEntityDto oneFieldEntityDto) {
        E entity = getFunction().apply(oneFieldEntityDto);
        dao.deleteEntity(entity);
//        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), oneFieldEntityDto.getValue());
//        dao.findEntityAndDelete(parameter);
        singleValueMap.removeEntity(entity.getClass(), oneFieldEntityDto.getValue());
    }

    @Override
    public ResponseOneFieldEntityDto getEntity(OneFieldEntityDto oneFieldEntityDto) {
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), oneFieldEntityDto.getValue());
        E entity = dao.getEntity(parameter);
        return transformationFunctionService.getEntity(ResponseOneFieldEntityDto.class, entity);
    }

    @Override
    public Optional<ResponseOneFieldEntityDto> getEntityOptional(OneFieldEntityDto oneFieldEntityDto) {
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), oneFieldEntityDto.getValue());
        E entity = dao.getEntity(parameter);
        return Optional.of(transformationFunctionService.getEntity(ResponseOneFieldEntityDto.class, entity));
    }

    @Override
    public List<ResponseOneFieldEntityDto> getEntities() {
        return dao.getEntityList().stream()
                .map(e -> (E) e)
                .map(entity ->
                        transformationFunctionService.getEntity(ResponseOneFieldEntityDto.class, entity)
                )
                .toList();
    }
}
