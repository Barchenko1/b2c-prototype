package com.b2c.prototype.service.processor.address.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.address.ICountryService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class CountryService extends AbstractConstantEntityService<Country> implements ICountryService {
    public CountryService(IParameterFactory parameterFactory,
                          IEntityDao dao,
                          ITransformationFunctionService transformationFunctionService,
                          ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, transformationFunctionService, singleValueMap);
    }
}
