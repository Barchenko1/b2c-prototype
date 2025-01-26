package com.b2c.prototype.service.processor.store.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.store.ICountTypeService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class CountTypeService extends AbstractConstantEntityService<ConstantPayloadDto, CountType> implements ICountTypeService {

    public CountTypeService(IParameterFactory parameterFactory,
                            IEntityDao dao,
                            ITransformationFunctionService transformationFunctionService,
                            ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, CountType.class),
                transformationFunctionService.getTransformationFunction(CountType.class, ConstantPayloadDto.class));
    }
}
