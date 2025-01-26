package com.b2c.prototype.service.processor.rating.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.AbstractIntegerConstantEntityService;
import com.b2c.prototype.service.processor.rating.IRatingService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class RatingService extends AbstractIntegerConstantEntityService<NumberConstantPayloadDto, Rating> implements IRatingService {

    public RatingService(IParameterFactory parameterFactory,
                         IEntityDao dao,
                         ITransformationFunctionService transformationFunctionService,
                         ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(NumberConstantPayloadDto.class, Rating.class),
                transformationFunctionService.getTransformationFunction(Rating.class, NumberConstantPayloadDto.class));
    }
}
