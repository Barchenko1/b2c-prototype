package com.b2c.prototype.service.processor.price.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.price.ICurrencyService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class CurrencyService extends AbstractConstantEntityService<ConstantPayloadDto, Currency> implements ICurrencyService {

    public CurrencyService(IParameterFactory parameterFactory,
                           IEntityDao dao,
                           ITransformationFunctionService transformationFunctionService,
                           ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, Currency.class),
                transformationFunctionService.getTransformationFunction(Currency.class, ConstantPayloadDto.class));
    }
}
