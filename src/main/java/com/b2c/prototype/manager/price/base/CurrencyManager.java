package com.b2c.prototype.manager.price.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.price.ICurrencyManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;

public class CurrencyManager extends AbstractConstantEntityManager<ConstantPayloadDto, Currency> implements ICurrencyManager {

    public CurrencyManager(IParameterFactory parameterFactory,
                           ITransactionEntityDao dao,
                           ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory,
                dao,
                new String[] {"Currency.findByValue", "Currency.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, Currency.class),
                transformationFunctionService.getTransformationFunction(Currency.class, ConstantPayloadDto.class));
    }
}
