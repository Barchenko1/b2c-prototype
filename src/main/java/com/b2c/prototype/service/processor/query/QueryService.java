package com.b2c.prototype.service.processor.query;

import com.b2c.prototype.modal.entity.message.MessageBox;
import com.tm.core.dao.query.ISearchWrapper;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class QueryService implements IQueryService {

    private final ISearchWrapper searchWrapper;

    public QueryService(ISearchWrapper searchWrapper) {
        this.searchWrapper = searchWrapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E getEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier) {
        return (E) searchWrapper.getEntitySupplier(clazz, parameterSupplier.get()).get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> Optional<E> getOptionalEntity(Class<?> clazz, Supplier<Parameter> parameterSupplier) {
        return Optional.ofNullable((E) searchWrapper.getEntitySupplier(clazz, parameterSupplier.get()).get());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> List<E> getEntityList(Class<?> clazz) {
        return (List<E>) searchWrapper.getEntityListSupplier(clazz).get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> List<E> getSubEntityList(Class<?> clazz, Supplier<Parameter> parameterSupplier) {
        return (List<E>) searchWrapper.getEntityListSupplier(clazz, parameterSupplier.get()).get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, E> R getEntityDto(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = (E) searchWrapper.getEntitySupplier(clazz, parameterSupplier.get()).get();
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, E> Optional<R> getOptionalEntityDto(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        E entity = (E) searchWrapper.getEntitySupplier(clazz, parameterSupplier.get()).get();
        return Optional.ofNullable(entity).map(mapToDtoFunction);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, E> List<R> getEntityDtoList(Class<?> clazz, Function<E, R> mapToDtoFunction) {
        List<E> entityList = (List<E>) searchWrapper.getEntityListSupplier(clazz).get();
        return entityList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, E> List<R> getSubEntityDtoList(Class<?> clazz, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        List<E> subList = (List<E>) searchWrapper.getEntityListSupplier(clazz, parameterSupplier.get()).get();
        return subList.stream()
                .map(mapToDtoFunction)
                .toList();
    }

    @Override
    public  <E> E getQueryEntity(Query<E> query, Supplier<Parameter> parameterSupplier) {
        Parameter parameter = parameterSupplier.get();
        query.setParameter(parameter.getName(), parameter.getValue());
        return query.uniqueResult();
    }
}
