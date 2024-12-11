package com.b2c.prototype.service.supplier;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SupplierService implements ISupplierService {

    private final IParameterFactory parameterFactory;
    private final ITransformationFunctionService transformationFunctionService;
    private final Map<String, Supplier<?>> supplierMap = new HashMap<>();

    public SupplierService(IParameterFactory parameterFactory,
                           ITransformationFunctionService transformationFunctionService) {
        this.parameterFactory = parameterFactory;
        this.transformationFunctionService = transformationFunctionService;
    }

    @Override
    public <E, R> Supplier<R> getSupplier(Class<R> classTo, E dataEntity, String sol) {
        return buildSupplier(classTo, dataEntity, sol);
    }

    @Override
    public Supplier<Parameter> getParameterSupplier(String key, String value) {
        return () -> parameterFactory.createStringParameter(key, value);
    }

    private <E> void addSupplier(Class<E> classTo, E dataEntity, String sol) {
        this.supplierMap.put(createKey(classTo, sol), buildSupplier(classTo, dataEntity, sol));
    }

    private <R> String createKey(Class<R> classTo, String sol) {
        return sol != null && !sol.isEmpty()
                ? classTo.getName() + "[" + sol+ "]"
                : classTo.getName();
    }

    private <E, R> Supplier<R> buildSupplier(Class<R> classTo, E dataEntity, String sol) {
        return () -> transformationFunctionService.getEntity(classTo, dataEntity, sol);
    }
}
