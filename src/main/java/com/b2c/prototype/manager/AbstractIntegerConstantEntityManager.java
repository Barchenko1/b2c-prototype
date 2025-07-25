package com.b2c.prototype.manager;

import com.b2c.prototype.modal.base.constant.AbstractNumberConstantEntity;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.process.dao.common.ITransactionEntityDao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.VALUE;

public abstract class AbstractIntegerConstantEntityManager<T, E extends AbstractNumberConstantEntity> implements IIntegerConstantEntityManager<T> {

    private final IParameterFactory parameterFactory;
    private final ITransactionEntityDao transactionEntityDao;
    private final String namedQuery;
    private final Function<T, E> mapDtoToEntityFunction;
    private final Function<E, T> mapEntityToDtoFunction;

    public AbstractIntegerConstantEntityManager(IParameterFactory parameterFactory,
                                                ITransactionEntityDao transactionEntityDao,
                                                String namedQuery,
                                                Function<T, E> mapDtoToEntityFunction,
                                                Function<E, T> mapEntityToDtoFunction) {
        this.parameterFactory = parameterFactory;
        this.transactionEntityDao = transactionEntityDao;
        this.namedQuery = namedQuery;
        this.mapDtoToEntityFunction = mapDtoToEntityFunction;
        this.mapEntityToDtoFunction = mapEntityToDtoFunction;
    }

    @Override
    public void saveEntity(T numberConstantPayloadDto) {
        E entity = mapDtoToEntityFunction.apply((T) numberConstantPayloadDto);
        transactionEntityDao.persistEntity(entity);
    }

    @Override
    public void updateEntity(Integer searchValue, T numberConstantPayloadDto) {
        E entity = mapDtoToEntityFunction.apply(numberConstantPayloadDto);
        Parameter parameter = parameterFactory.createIntegerParameter(VALUE, searchValue);
        transactionEntityDao.executeConsumer(session -> {
            E fetchedEntity = transactionEntityDao.getNamedQueryEntity(namedQuery, parameter);
            fetchedEntity.setValue(entity.getValue());
            transactionEntityDao.mergeEntity(fetchedEntity);
        });
    }

    @Override
    public void deleteEntity(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        transactionEntityDao.executeConsumer(session -> {
            E fetchedEntity = transactionEntityDao.getNamedQueryEntity(namedQuery, parameter);
            transactionEntityDao.deleteEntity(fetchedEntity);
        });
    }

    @Override
    public NumberConstantPayloadDto getEntity(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        E entity = transactionEntityDao.getNamedQueryEntity(namedQuery, parameter);
        return (NumberConstantPayloadDto) mapEntityToDtoFunction.apply(entity);
    }

    @Override
    public Optional<NumberConstantPayloadDto> getEntityOptional(int ratingValue) {
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, ratingValue);
        E entity = transactionEntityDao.getNamedQueryEntity(namedQuery, parameter);
        return Optional.of((NumberConstantPayloadDto) mapEntityToDtoFunction.apply(entity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NumberConstantPayloadDto> getEntities() {
        return (List<NumberConstantPayloadDto>) transactionEntityDao.getNamedQueryEntityList(namedQuery).stream()
                .map(entity -> mapEntityToDtoFunction.apply((E) entity))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Class<E> resolveEntityClass() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) type.getActualTypeArguments()[1];
    }
}
