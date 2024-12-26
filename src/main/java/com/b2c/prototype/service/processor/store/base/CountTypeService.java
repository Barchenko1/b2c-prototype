package com.b2c.prototype.service.processor.store.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.store.ICountTypeService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class CountTypeService extends AbstractOneFieldEntityService<CountType> implements ICountTypeService {

    public CountTypeService(IParameterFactory parameterFactory,
                            IEntityDao dao,
                            ITransformationFunctionService transformationFunctionService,
                            ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, transformationFunctionService, singleValueMap);
    }

    @Override
    public Function<OneFieldEntityDto, CountType> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, CountType.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
