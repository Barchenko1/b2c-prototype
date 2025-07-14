package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.item.IItemStatusManager;
import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;

public class ArticularStatusManager extends AbstractConstantEntityManager<ConstantPayloadDto, ArticularStatus> implements IItemStatusManager {
    public ArticularStatusManager(IParameterFactory parameterFactory,
                                  ITransactionEntityDao itemStatusDao,
                                  ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, itemStatusDao,
                new String[] {"ArticularStatus.findByValue", "ArticularStatus.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, ArticularStatus.class),
                transformationFunctionService.getTransformationFunction(ArticularStatus.class, ConstantPayloadDto.class));
    }
}
