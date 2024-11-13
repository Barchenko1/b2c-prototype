package com.b2c.prototype.service.processor.store.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.store.ICountTypeService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class CountTypeService extends AbstractOneFieldEntityService<CountType> implements ICountTypeService {

    public CountTypeService(IParameterFactory parameterFactory, ISingleEntityDao dao, IEntityCachedMap entityCachedMap) {
        super(parameterFactory, dao, entityCachedMap);
    }

    @Override
    public Function<OneFieldEntityDto, CountType> getFunction() {
        return oneFieldEntityDto -> CountType.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
