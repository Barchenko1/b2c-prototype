package com.b2c.prototype.service.processor;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDtoUpdate;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractOneIntegerFieldEntityService<E> implements IOneIntegerFieldEntityService<E> {

    private final IParameterFactory parameterFactory;
    private final ISingleEntityDao dao;
    private final IEntityCachedMap entityCachedMap;

    public AbstractOneIntegerFieldEntityService(IParameterFactory parameterFactory,
                                                ISingleEntityDao dao,
                                                IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.dao = dao;
        this.entityCachedMap = entityCachedMap;
    }

    protected abstract Function<OneIntegerFieldEntityDto, E> getFunction();
    protected abstract String getFieldName();

    @Override
    public void saveEntity(OneIntegerFieldEntityDto oneIntegerFieldEntityDto) {
        E entity = getFunction().apply(oneIntegerFieldEntityDto);
        dao.saveEntity(entity);
        entityCachedMap.putEntity(entity.getClass(), getFieldName(), entity);
    }

    @Override
    public void updateEntity(OneIntegerFieldEntityDtoUpdate oneFieldEntityDtoUpdate) {
        OneIntegerFieldEntityDto newEntityRequest = oneFieldEntityDtoUpdate.getNewEntityDto();
        E entity = getFunction().apply(newEntityRequest);
        Integer searchParameter = oneFieldEntityDtoUpdate.getOldEntityDto().getValue();
        Parameter parameter = parameterFactory.createIntegerParameter(getFieldName(), searchParameter);
        dao.findEntityAndUpdate(entity, parameter);
        entityCachedMap.updateEntity(
                entity.getClass(),
                searchParameter,
                newEntityRequest.getValue(),
                entity);
    }

    @Override
    public void deleteEntity(OneIntegerFieldEntityDto oneIntegerFieldEntityDto) {
        E entity = getFunction().apply(oneIntegerFieldEntityDto);
        dao.deleteEntity(entity);
        entityCachedMap.removeEntity(entity.getClass(), oneIntegerFieldEntityDto.getValue());
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
