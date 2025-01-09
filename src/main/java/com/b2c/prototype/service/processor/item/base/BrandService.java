package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.item.IBrandService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class BrandService extends AbstractConstantEntityService<Brand> implements IBrandService {

    public BrandService(IParameterFactory parameterFactory,
                        IEntityDao brandDao,
                        ITransformationFunctionService transformationFunctionService,
                        ISingleValueMap singleValueMap) {
        super(parameterFactory, brandDao, transformationFunctionService, singleValueMap);
    }
}
