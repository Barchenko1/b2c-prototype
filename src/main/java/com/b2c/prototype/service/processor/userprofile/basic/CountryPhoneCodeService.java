package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.userprofile.ICountryPhoneCodeService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class CountryPhoneCodeService extends AbstractConstantEntityService<ConstantPayloadDto, CountryPhoneCode> implements ICountryPhoneCodeService {

    public CountryPhoneCodeService(IParameterFactory parameterFactory,
                                   IEntityDao countryPhoneCodeDao,
                                   ITransformationFunctionService transformationFunctionService,
                                   ISingleValueMap singleValueMap) {
        super(parameterFactory, countryPhoneCodeDao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, CountryPhoneCode.class),
                transformationFunctionService.getTransformationFunction(CountryPhoneCode.class, ConstantPayloadDto.class));
    }
}
