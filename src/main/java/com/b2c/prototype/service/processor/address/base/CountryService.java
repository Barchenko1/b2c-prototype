package com.b2c.prototype.service.processor.address.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.address.ICountryService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class CountryService extends AbstractOneFieldEntityService<Country> implements ICountryService {
    public CountryService(IParameterFactory parameterFactory, IEntityDao dao, IEntityCachedMap entityCachedMap) {
        super(parameterFactory, dao, entityCachedMap);
    }

    @Override
    protected Function<OneFieldEntityDto, Country> getFunction() {
        return oneFieldEntityDto -> Country.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
