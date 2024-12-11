package com.b2c.prototype.service.processor.rating.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.service.processor.AbstractOneIntegerFieldEntityService;
import com.b2c.prototype.service.processor.rating.IRatingService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class RatingService extends AbstractOneIntegerFieldEntityService<Rating> implements IRatingService {
    public RatingService(IParameterFactory parameterFactory, IEntityDao dao, IEntityCachedMap entityCachedMap) {
        super(parameterFactory, dao, entityCachedMap);
    }

    @Override
    protected Function<OneIntegerFieldEntityDto, Rating> getFunction() {
        return oneFieldEntityDto -> Rating.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
