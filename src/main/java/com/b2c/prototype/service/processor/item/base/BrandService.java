package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.item.IBrandService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class BrandService extends AbstractOneFieldEntityService<Brand> implements IBrandService {

    public BrandService(IParameterFactory parameterFactory,
                        IEntityDao brandDao,
                        ITransformationFunctionService transformationFunctionService,
                        ISingleValueMap singleValueMap) {
        super(parameterFactory, brandDao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneFieldEntityDto, Brand> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, Brand.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
