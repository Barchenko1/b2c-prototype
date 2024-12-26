package com.b2c.prototype.service.processor.store.base;

import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.supplier.ISupplierService;

public class StoreService {

    private final IEntityOperationDao entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public StoreService(IEntityOperationDao entityOperationDao, ITransformationFunctionService transformationFunctionService, ISupplierService supplierService) {
        this.entityOperationDao = entityOperationDao;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }


}
