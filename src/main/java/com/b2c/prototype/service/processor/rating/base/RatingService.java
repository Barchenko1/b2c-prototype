package com.b2c.prototype.service.processor.rating.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneIntegerFieldEntityService;
import com.b2c.prototype.service.processor.rating.IRatingService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class RatingService extends AbstractOneIntegerFieldEntityService<Rating> implements IRatingService {

    public RatingService(IParameterFactory parameterFactory,
                         IEntityDao dao,
                         ITransformationFunctionService transformationFunctionService,
                         ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneIntegerFieldEntityDto, Rating> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneIntegerFieldEntityDto.class, Rating.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
