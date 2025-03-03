package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.message.IMessageStatusManager;

import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class MessageStatusManager extends AbstractConstantEntityManager<ConstantPayloadDto, MessageStatus> implements IMessageStatusManager {
    public MessageStatusManager(IParameterFactory parameterFactory,
                                IEntityDao dao,
                                ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, dao,
                "",
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, MessageStatus.class),
                transformationFunctionService.getTransformationFunction(MessageStatus.class, ConstantPayloadDto.class));
    }
}
