package com.b2c.prototype.manager.userprofile.basic;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.userprofile.ICountryPhoneCodeManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class CountryPhoneCodeManager extends AbstractConstantEntityManager<ConstantPayloadDto, CountryPhoneCode> implements ICountryPhoneCodeManager {

    public CountryPhoneCodeManager(IParameterFactory parameterFactory,
                                   IEntityDao countryPhoneCodeDao,
                                   ITransformationFunctionService transformationFunctionService,
                                   IConstantsScope singleValueMap) {
        super(parameterFactory, countryPhoneCodeDao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, CountryPhoneCode.class),
                transformationFunctionService.getTransformationFunction(CountryPhoneCode.class, ConstantPayloadDto.class));
    }
}
