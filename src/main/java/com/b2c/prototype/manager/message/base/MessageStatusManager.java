package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.IEntityDao;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.message.IMessageStatusManager;

import com.tm.core.finder.factory.IParameterFactory;

public class MessageStatusManager extends AbstractConstantEntityManager<ConstantPayloadDto, MessageStatus> implements IMessageStatusManager {
    public MessageStatusManager(IParameterFactory parameterFactory,
                                IGeneralEntityDao dao,
                                ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory,
                dao,
                new String[] {"MessageStatus.findByValue", "MessageStatus.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, MessageStatus.class),
                transformationFunctionService.getTransformationFunction(MessageStatus.class, ConstantPayloadDto.class));
    }
}
