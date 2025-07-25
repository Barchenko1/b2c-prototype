package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.store.ICountTypeManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;

public class CountTypeManager extends AbstractConstantEntityManager<ConstantPayloadDto, CountType> implements ICountTypeManager {

    public CountTypeManager(IParameterFactory parameterFactory,
                            ITransactionEntityDao dao,
                            ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory,
                dao,
                new String[] {"CountType.findByValue", "CountType.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, CountType.class),
                transformationFunctionService.getTransformationFunction(CountType.class, ConstantPayloadDto.class));
    }
}
