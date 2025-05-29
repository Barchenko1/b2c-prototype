package com.b2c.prototype.manager.message.base;


import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.message.IMessageTypeManager;
import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class MessageTypeManager extends AbstractConstantEntityManager<ConstantPayloadDto, MessageType> implements IMessageTypeManager {
    public MessageTypeManager(IParameterFactory parameterFactory,
                              IEntityDao dao,
                              ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, dao,
                new String[] {"MessageType.findByValue", "MessageType.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, MessageType.class),
                transformationFunctionService.getTransformationFunction(MessageType.class, ConstantPayloadDto.class)
        );
    }
}
