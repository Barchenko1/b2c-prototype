package com.b2c.prototype.manager;

import com.b2c.prototype.modal.base.IConstant;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.process.dao.common.IEntityDao;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.b2c.prototype.util.Constant.VALUE;

public abstract class AbstractConstantEntityManager<T, E extends IConstant> implements IConstantEntityManager<T> {

    private final IParameterFactory parameterFactory;
    private final IEntityDao dao;
    private final String[] namedQueries;
    private final Function<T, E> mapDtoToEntityFunction;
    private final Function<E, T> mapEntityToDtoFunction;

    public AbstractConstantEntityManager(IParameterFactory parameterFactory,
                                         IEntityDao dao,
                                         String[] namedQueries,
                                         Function<T, E> mapDtoToEntityFunction,
                                         Function<E, T> mapEntityToDtoFunction) {
        this.parameterFactory = parameterFactory;
        this.dao = dao;
        this.namedQueries = namedQueries;
        this.mapDtoToEntityFunction = mapDtoToEntityFunction;
        this.mapEntityToDtoFunction = mapEntityToDtoFunction;
    }

    @Override
    public void saveEntity(T payload) {
        E entity = mapDtoToEntityFunction.apply(payload);
        dao.persistEntity(entity);
    }

    @Override
    public void updateEntity(String searchValue, T payload) {
        E entity = mapDtoToEntityFunction.apply(payload);
        Parameter parameter = parameterFactory.createStringParameter(VALUE, searchValue);
        dao.findEntityAndUpdate(entity, parameter);
    }

    @Override
    public void deleteEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        dao.findEntityAndDelete(parameter);
    }

    @Override
    public T getEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        E entity = dao.getNamedQueryEntity(namedQueries[0], parameter);
        return mapEntityToDtoFunction.apply(entity);
    }

    @Override
    public Optional<T> getEntityOptional(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        E entity = dao.getNamedQueryEntity(namedQueries[0], parameter);
        return Optional.of(mapEntityToDtoFunction.apply(entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getEntities() {
        return dao.getNamedQueryEntityList(namedQueries[1]).stream()
                .map(e -> (E) e)
                .map(mapEntityToDtoFunction)
                .toList();
    }
}
