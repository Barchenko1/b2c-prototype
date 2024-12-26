package com.b2c.prototype.service.processor.option.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.option.IOptionGroupService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class OptionGroupService extends AbstractOneFieldEntityService<OptionGroup> implements IOptionGroupService {

    public OptionGroupService(IParameterFactory parameterFactory,
                              IEntityDao optionGroupDao,
                              ITransformationFunctionService transformationFunctionService,
                              ISingleValueMap singleValueMap) {
        super(parameterFactory, optionGroupDao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneFieldEntityDto, OptionGroup> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, OptionGroup.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
