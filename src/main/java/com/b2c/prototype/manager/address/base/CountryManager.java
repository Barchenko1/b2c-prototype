package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.address.ICountryManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;

public class CountryManager extends AbstractConstantEntityManager<CountryDto, Country> implements ICountryManager {
    public CountryManager(IParameterFactory parameterFactory,
                          ITransactionEntityDao dao,
                          ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory,
                dao,
                new String[] {"Country.findByValue", "Country.all"},
                transformationFunctionService.getTransformationFunction(CountryDto.class, Country.class),
                transformationFunctionService.getTransformationFunction(Country.class, CountryDto.class));
    }
}
