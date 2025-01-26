package com.b2c.prototype.service.manager.rating.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.manager.AbstractIntegerConstantEntityManager;
import com.b2c.prototype.service.manager.rating.IRatingManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class RatingManager extends AbstractIntegerConstantEntityManager<NumberConstantPayloadDto, Rating> implements IRatingManager {

    public RatingManager(IParameterFactory parameterFactory,
                         IEntityDao dao,
                         ITransformationFunctionService transformationFunctionService,
                         ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(NumberConstantPayloadDto.class, Rating.class),
                transformationFunctionService.getTransformationFunction(Rating.class, NumberConstantPayloadDto.class));
    }
}
