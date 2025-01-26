package com.b2c.prototype.service.manager.address.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.CountryDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.manager.AbstractConstantEntityManager;
import com.b2c.prototype.service.manager.address.ICountryManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class CountryManager extends AbstractConstantEntityManager<CountryDto, Country> implements ICountryManager {
    public CountryManager(IParameterFactory parameterFactory,
                          IEntityDao dao,
                          ITransformationFunctionService transformationFunctionService,
                          ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(CountryDto.class, Country.class),
                transformationFunctionService.getTransformationFunction(Country.class, CountryDto.class));
    }
}
