package com.b2c.prototype.service.supplier;

import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import org.hibernate.Session;

import java.util.function.Function;
import java.util.function.Supplier;

public class SupplierService implements ISupplierService {

    private final IParameterFactory parameterFactory;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;

    public SupplierService(IParameterFactory parameterFactory,
                           ISearchService searchService,
                           ITransformationFunctionService transformationFunctionService) {
        this.searchService = searchService;
        this.parameterFactory = parameterFactory;
        this.transformationFunctionService = transformationFunctionService;
    }

    @Override
    public <E, R> Supplier<R> entityFieldSupplier(Class<E> entityClass,
                                                  String namedQuery,
                                                  Supplier<Parameter> parameterSupplier,
                                                  Function<E, R> fieldExtractor) {
        return () -> {
            E entity = searchService.getNamedQueryEntity(entityClass, namedQuery, parameterSupplier.get());
            return fieldExtractor.apply(entity);
        };
    }

    @Override
    public <E> Supplier<E> entityFieldSupplier(Class<E> entityClass,
                                               String namedQuery,
                                               Supplier<Parameter> parameterSupplier) {
        return () -> searchService.getNamedQueryEntity(entityClass, namedQuery, parameterSupplier.get());
    }

    @Override
    public <E, R> Supplier<R> getSupplier(Class<R> classTo, E dataEntity) {
        return buildSupplier(classTo, dataEntity, null);
    }

    @Override
    public <E, R> Supplier<R> getSupplier(Class<R> classTo, E dataEntity, String sol) {
        return buildSupplier(classTo, dataEntity, sol);
    }

    @Override
    public <E, R> Supplier<R> getSupplier(Session session, Class<R> classTo, E dataEntity) {
        return buildSupplier(session, classTo, dataEntity, null);
    }

    @Override
    public <E, R> Supplier<R> getSupplier(Session session, Class<R> classTo, E dataEntity, String sol) {
        return buildSupplier(session, classTo, dataEntity, sol);
    }

    @Override
    public Supplier<Parameter> parameterStringSupplier(String key, String value) {
        return () -> parameterFactory.createStringParameter(key, value);
    }

    private <E, R> Supplier<R> buildSupplier(Class<R> classTo, E dataEntity, String sol) {
        return () -> transformationFunctionService.getEntity(classTo, dataEntity, sol);
    }

    private <E, R> Supplier<R> buildSupplier(Session session, Class<R> classTo, E dataEntity, String sol) {
        return () -> transformationFunctionService.getEntity(session, classTo, dataEntity, sol);
    }
}
