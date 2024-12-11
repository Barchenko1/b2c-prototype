package com.b2c.prototype.service.supplier;

import com.tm.core.processor.finder.parameter.Parameter;
import java.util.function.Supplier;

public interface ISupplierService {
    <E, R> Supplier<R> getSupplier(Class<R> classTo, E dataEntity, String sol);
    Supplier<Parameter> getParameterSupplier(String key, String value);
}
