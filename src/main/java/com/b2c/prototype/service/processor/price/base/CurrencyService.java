package com.b2c.prototype.service.processor.price.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.price.ICurrencyService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class CurrencyService extends AbstractOneFieldEntityService<Currency> implements ICurrencyService {

    public CurrencyService(IParameterFactory parameterFactory,
                           IEntityDao dao,
                           ITransformationFunctionService transformationFunctionService,
                           ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneFieldEntityDto, Currency> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, Currency.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
