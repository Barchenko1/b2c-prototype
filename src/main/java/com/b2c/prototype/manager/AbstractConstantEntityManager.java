package com.b2c.prototype.manager;

import com.b2c.prototype.modal.base.constant.AbstractConstantEntity;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.process.dao.common.ITransactionEntityDao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.b2c.prototype.util.Constant.VALUE;

public abstract class AbstractConstantEntityManager<T, E extends AbstractConstantEntity> implements IConstantEntityManager<T> {

    private final IParameterFactory parameterFactory;
    private final ITransactionEntityDao transactionEntityDao;
    private final String[] namedQueries;
    private final Function<T, E> mapDtoToEntityFunction;
    private final Function<E, T> mapEntityToDtoFunction;

    public AbstractConstantEntityManager(IParameterFactory parameterFactory,
                                         ITransactionEntityDao transactionEntityDao,
                                         String[] namedQueries,
                                         Function<T, E> mapDtoToEntityFunction,
                                         Function<E, T> mapEntityToDtoFunction) {
        this.parameterFactory = parameterFactory;
        this.transactionEntityDao = transactionEntityDao;
        this.namedQueries = namedQueries;
        this.mapDtoToEntityFunction = mapDtoToEntityFunction;
        this.mapEntityToDtoFunction = mapEntityToDtoFunction;
    }

    @Override
    public void saveEntity(T payload) {
        E entity = mapDtoToEntityFunction.apply(payload);
        transactionEntityDao.persistEntity(entity);
    }

    @Override
    public void updateEntity(String searchValue, T payload) {
        E entity = mapDtoToEntityFunction.apply(payload);
        Parameter parameter = parameterFactory.createStringParameter(VALUE, searchValue);
        transactionEntityDao.executeConsumer(session -> {
            E fetchedEntity = transactionEntityDao.getNamedQueryEntityClose(namedQueries[0], parameter);
            fetchedEntity.setValue(entity.getValue());
            fetchedEntity.setLabel(entity.getLabel());
            transactionEntityDao.mergeEntity(fetchedEntity);
        });
    }

    @Override
    public void deleteEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        transactionEntityDao.executeConsumer(session -> {
            E fetchedEntity = transactionEntityDao.getNamedQueryEntityClose(namedQueries[0], parameter);
            transactionEntityDao.deleteEntity(fetchedEntity);
        });
    }

    @Override
    public T getEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        E entity = transactionEntityDao.getNamedQueryEntityClose(namedQueries[0], parameter);
        return mapEntityToDtoFunction.apply(entity);
    }

    @Override
    public Optional<T> getEntityOptional(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        E entity = transactionEntityDao.getNamedQueryEntityClose(namedQueries[0], parameter);
        return Optional.of(mapEntityToDtoFunction.apply(entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getEntities() {
        return transactionEntityDao.getNamedQueryEntityListClose(namedQueries[1]).stream()
                .map(entity -> mapEntityToDtoFunction.apply((E) entity))
                .toList();
    }

    @SuppressWarnings("unchecked")
    private Class<E> resolveEntityClass() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) type.getActualTypeArguments()[1];
    }
}
