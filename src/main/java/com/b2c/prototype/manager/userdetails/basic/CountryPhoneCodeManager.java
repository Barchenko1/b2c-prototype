package com.b2c.prototype.manager.userdetails.basic;


import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.userdetails.ICountryPhoneCodeManager;
import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class CountryPhoneCodeManager extends AbstractConstantEntityManager<ConstantPayloadDto, CountryPhoneCode> implements ICountryPhoneCodeManager {

    public CountryPhoneCodeManager(IParameterFactory parameterFactory,
                                   IEntityDao countryPhoneCodeDao,
                                   ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, countryPhoneCodeDao,
                new String[] {"CountryPhoneCode.findByValue", "CountryPhoneCode.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, CountryPhoneCode.class),
                transformationFunctionService.getTransformationFunction(CountryPhoneCode.class, ConstantPayloadDto.class));
    }
}
