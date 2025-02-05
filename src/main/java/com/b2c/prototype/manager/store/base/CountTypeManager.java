package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.store.ICountTypeManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class CountTypeManager extends AbstractConstantEntityManager<ConstantPayloadDto, CountType> implements ICountTypeManager {

    public CountTypeManager(IParameterFactory parameterFactory,
                            IEntityDao dao,
                            ITransformationFunctionService transformationFunctionService,
                            IConstantsScope singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, CountType.class),
                transformationFunctionService.getTransformationFunction(CountType.class, ConstantPayloadDto.class));
    }
}
