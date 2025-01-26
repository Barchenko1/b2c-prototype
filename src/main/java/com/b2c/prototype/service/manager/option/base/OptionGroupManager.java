package com.b2c.prototype.service.manager.option.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.manager.AbstractConstantEntityManager;
import com.b2c.prototype.service.manager.option.IOptionGroupManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class OptionGroupManager extends AbstractConstantEntityManager<ConstantPayloadDto, OptionGroup> implements IOptionGroupManager {

    public OptionGroupManager(IParameterFactory parameterFactory,
                              IEntityDao optionGroupDao,
                              ITransformationFunctionService transformationFunctionService,
                              ISingleValueMap singleValueMap) {
        super(parameterFactory, optionGroupDao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, OptionGroup.class),
                transformationFunctionService.getTransformationFunction(OptionGroup.class, ConstantPayloadDto.class)
        );
    }
}
