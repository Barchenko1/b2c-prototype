package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.dto.payload.CountryDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.address.ICountryManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class CountryManager extends AbstractConstantEntityManager<CountryDto, Country> implements ICountryManager {
    public CountryManager(IParameterFactory parameterFactory,
                          IEntityDao dao,
                          ITransformationFunctionService transformationFunctionService,
                          IConstantsScope singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(CountryDto.class, Country.class),
                transformationFunctionService.getTransformationFunction(Country.class, CountryDto.class));
    }
}
