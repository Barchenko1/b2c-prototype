package com.b2c.prototype.manager.price.base;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.price.ICurrencyManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class CurrencyManager extends AbstractConstantEntityManager<ConstantPayloadDto, Currency> implements ICurrencyManager {

    public CurrencyManager(IParameterFactory parameterFactory,
                           IEntityDao dao,
                           ITransformationFunctionService transformationFunctionService,
                           IConstantsScope singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, Currency.class),
                transformationFunctionService.getTransformationFunction(Currency.class, ConstantPayloadDto.class));
    }
}
