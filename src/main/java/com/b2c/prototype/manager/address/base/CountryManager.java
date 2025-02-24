package com.b2c.prototype.manager.address.base;


import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.address.ICountryManager;
import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class CountryManager extends AbstractConstantEntityManager<CountryDto, Country> implements ICountryManager {
    public CountryManager(IParameterFactory parameterFactory,
                          IEntityDao dao,
                          ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, dao,
                transformationFunctionService.getTransformationFunction(CountryDto.class, Country.class),
                transformationFunctionService.getTransformationFunction(Country.class, CountryDto.class));
    }
}
