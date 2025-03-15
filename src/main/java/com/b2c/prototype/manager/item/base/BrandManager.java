package com.b2c.prototype.manager.item.base;


import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.item.IBrandManager;
import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class BrandManager extends AbstractConstantEntityManager<ConstantPayloadDto, Brand> implements IBrandManager {

    public BrandManager(IParameterFactory parameterFactory,
                        IEntityDao brandDao,
                        ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, brandDao,
                new String[] {"Brand.findByValue", "Brand.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, Brand.class),
                transformationFunctionService.getTransformationFunction(Brand.class, ConstantPayloadDto.class));
    }
}
