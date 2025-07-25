package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;

public class OptionGroupManager extends AbstractConstantEntityManager<ConstantPayloadDto, OptionGroup> implements IOptionGroupManager {

    public OptionGroupManager(IParameterFactory parameterFactory,
                              ITransactionEntityDao optionGroupDao,
                              ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory,
                optionGroupDao,
                new String[] {"OptionGroup.findByValue", "OptionGroup.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, OptionGroup.class),
                transformationFunctionService.getTransformationFunction(OptionGroup.class, ConstantPayloadDto.class)
        );
    }
}
