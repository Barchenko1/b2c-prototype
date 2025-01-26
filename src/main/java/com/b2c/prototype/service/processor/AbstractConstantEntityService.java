package com.b2c.prototype.service.processor;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.base.IConstant;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.b2c.prototype.util.Constant.VALUE;

public abstract class AbstractConstantEntityService<T, E extends IConstant> implements IConstantEntityService<T> {

    private final IParameterFactory parameterFactory;
    private final IEntityDao dao;
    private final ISingleValueMap singleValueMap;
    private final Function<T, E> mapDtoToEntityFunction;
    private final Function<E, T> mapEntityToDtoFunction;

//    protected abstract Object extractValue(T payload);

    public AbstractConstantEntityService(IParameterFactory parameterFactory,
                                         IEntityDao dao,
                                         ISingleValueMap singleValueMap,
                                         Function<T, E> mapDtoToEntityFunction,
                                         Function<E, T> mapEntityToDtoFunction) {
        this.parameterFactory = parameterFactory;
        this.dao = dao;
        this.singleValueMap = singleValueMap;
        this.mapDtoToEntityFunction = mapDtoToEntityFunction;
        this.mapEntityToDtoFunction = mapEntityToDtoFunction;
    }

    @Override
    public void saveEntity(T payload) {
        E entity = mapDtoToEntityFunction.apply(payload);
        dao.persistEntity(entity);
        singleValueMap.putEntity(entity.getClass(), VALUE, entity);
    }

    @Override
    public void updateEntity(String searchValue, T payload) {
        E entity = mapDtoToEntityFunction.apply(payload);
        Parameter parameter = parameterFactory.createStringParameter(VALUE, searchValue);
        dao.findEntityAndUpdate(entity, parameter);
        singleValueMap.putRemoveEntity(
                entity.getClass(),
                searchValue,
                entity.getValue(),
                entity);
    }

    @Override
    public void deleteEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        dao.findEntityAndDelete(parameter);
        singleValueMap.removeEntity(dao.getEntityClass(), value);
    }

    @Override
    public T getEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        E entity = dao.getEntity(parameter);
        return mapEntityToDtoFunction.apply(entity);
    }

    @Override
    public Optional<T> getEntityOptional(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        E entity = dao.getEntity(parameter);
        return Optional.of(mapEntityToDtoFunction.apply(entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getEntities() {
        return dao.getEntityList().stream()
                .map(e -> (E) e)
                .map(mapEntityToDtoFunction)
                .toList();
    }
}
