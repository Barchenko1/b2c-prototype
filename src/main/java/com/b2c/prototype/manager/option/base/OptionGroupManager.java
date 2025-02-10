package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class OptionGroupManager extends AbstractConstantEntityManager<ConstantPayloadDto, OptionGroup> implements IOptionGroupManager {

    public OptionGroupManager(IParameterFactory parameterFactory,
                              IEntityDao optionGroupDao,
                              ITransformationFunctionService transformationFunctionService,
                              IConstantsScope constantsScope) {
        super(parameterFactory, optionGroupDao, constantsScope,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, OptionGroup.class),
                transformationFunctionService.getTransformationFunction(OptionGroup.class, ConstantPayloadDto.class)
        );
    }
}
