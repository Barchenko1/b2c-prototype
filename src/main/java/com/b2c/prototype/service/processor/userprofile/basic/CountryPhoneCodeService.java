package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.userprofile.ICountryPhoneCodeService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class CountryPhoneCodeService extends AbstractOneFieldEntityService<CountryPhoneCode> implements ICountryPhoneCodeService {

    public CountryPhoneCodeService(IParameterFactory parameterFactory,
                                   ISingleEntityDao countryPhoneCodeDao,
                                   IEntityCachedMap entityCachedMap) {
        super(parameterFactory, countryPhoneCodeDao, entityCachedMap);
    }

    @Override
    protected Function<OneFieldEntityDto, CountryPhoneCode> getFunction() {
        return oneFieldEntityDto -> CountryPhoneCode.builder()
                .code(oneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "code";
    }
}
