package com.b2c.prototype.service.processor;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractOneFieldEntityService<E> implements IOneFieldEntityService<E> {

    private final IParameterFactory parameterFactory;
    private final IEntityDao dao;
    private final IEntityCachedMap entityCachedMap;

    public AbstractOneFieldEntityService(IParameterFactory parameterFactory,
                                         IEntityDao dao,
                                         IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.dao = dao;
        this.entityCachedMap = entityCachedMap;
    }

    protected abstract Function<OneFieldEntityDto, E> getFunction();
    protected abstract String getFieldName();

    @Override
    public void saveEntity(OneFieldEntityDto oneFieldEntityDto) {
        E entity = getFunction().apply(oneFieldEntityDto);
        dao.persistEntity(entity);
        entityCachedMap.putEntity(entity.getClass(), getFieldName(), entity);
    }

    @Override
    public void updateEntity(OneFieldEntityDtoUpdate oneFieldEntityDtoUpdate) {
        OneFieldEntityDto newEntityRequest = oneFieldEntityDtoUpdate.getNewEntityDto();
        E entity = getFunction().apply(newEntityRequest);
        String searchParameter = oneFieldEntityDtoUpdate.getOldEntityDto().getValue();
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), searchParameter);
        dao.findEntityAndUpdate(entity, parameter);
        entityCachedMap.updateEntity(
                entity.getClass(),
                searchParameter,
                newEntityRequest.getValue(),
                entity);
    }

    @Override
    public void deleteEntity(OneFieldEntityDto oneFieldEntityDto) {
        E entity = getFunction().apply(oneFieldEntityDto);
        dao.deleteEntity(entity);
        entityCachedMap.removeEntity(entity.getClass(), oneFieldEntityDto.getValue());
    }

    @Override
    public E getEntity(OneFieldEntityDto oneFieldEntityDto) {
        Parameter parameter = parameterFactory
                .createStringParameter(getFieldName(), oneFieldEntityDto.getValue());
        return dao.getEntity(parameter);
    }

    @Override
    public Optional<E> getEntityOptional(OneFieldEntityDto oneFieldEntityDto) {
        Parameter parameter = parameterFactory
                .createStringParameter(getFieldName(), oneFieldEntityDto.getValue());
        return dao.getOptionalEntity(parameter);
    }

    @Override
    public List<E> getEntities() {
        return dao.getEntityList();
    }
}
