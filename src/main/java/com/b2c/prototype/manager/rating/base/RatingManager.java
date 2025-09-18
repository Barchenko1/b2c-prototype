package com.b2c.prototype.manager.rating.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractIntegerConstantEntityManager;
import com.b2c.prototype.manager.rating.IRatingManager;
import com.tm.core.finder.factory.IParameterFactory;
import org.springframework.stereotype.Service;

@Service
public class RatingManager extends AbstractIntegerConstantEntityManager<NumberConstantPayloadDto, Rating> implements IRatingManager<NumberConstantPayloadDto> {

    public RatingManager(IParameterFactory parameterFactory,
                         IGeneralEntityDao dao,
                         ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory,
                dao,
                transformationFunctionService.getTransformationFunction(NumberConstantPayloadDto.class, Rating.class),
                transformationFunctionService.getTransformationFunction(Rating.class, NumberConstantPayloadDto.class));
    }
}
