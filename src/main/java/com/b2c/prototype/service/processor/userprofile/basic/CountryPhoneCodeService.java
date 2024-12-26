package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.userprofile.ICountryPhoneCodeService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class CountryPhoneCodeService extends AbstractOneFieldEntityService<CountryPhoneCode> implements ICountryPhoneCodeService {

    public CountryPhoneCodeService(IParameterFactory parameterFactory,
                                   IEntityDao countryPhoneCodeDao,
                                   ITransformationFunctionService transformationFunctionService,
                                   ISingleValueMap singleValueMap) {
        super(parameterFactory, countryPhoneCodeDao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneFieldEntityDto, CountryPhoneCode> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, CountryPhoneCode.class);
    }

    @Override
    protected String getFieldName() {
        return "code";
    }
}
