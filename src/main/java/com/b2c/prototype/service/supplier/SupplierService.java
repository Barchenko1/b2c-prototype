package com.b2c.prototype.service.supplier;

import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.IQueryService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.function.Function;
import java.util.function.Supplier;

public class SupplierService implements ISupplierService {

    private final IParameterFactory parameterFactory;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;

    public SupplierService(IParameterFactory parameterFactory,
                           IQueryService queryService,
                           ITransformationFunctionService transformationFunctionService) {
        this.queryService = queryService;
        this.parameterFactory = parameterFactory;
        this.transformationFunctionService = transformationFunctionService;
    }

    @Override
    public <E, R> Supplier<R> entityFieldSupplier(Class<E> entityClass,
                                                  Supplier<Parameter> parameterSupplier,
                                                  Function<E, R> fieldExtractor) {
        return () -> {
            E entity = queryService.getEntity(entityClass, parameterSupplier);
            return fieldExtractor.apply(entity);
        };
    }

    @Override
    public <E> Supplier<E> entityFieldSupplier(Class<E> entityClass, Supplier<Parameter> parameterSupplier) {
        return () -> queryService.getEntity(entityClass, parameterSupplier);
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
    public Supplier<Parameter> parameterStringSupplier(String key, String value) {
        return () -> parameterFactory.createStringParameter(key, value);
    }

    private <E, R> Supplier<R> buildSupplier(Class<R> classTo, E dataEntity, String sol) {
        return () -> transformationFunctionService.getEntity(classTo, dataEntity, sol);
    }
}
