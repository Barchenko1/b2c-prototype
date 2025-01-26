package com.b2c.prototype.service.manager.message.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.manager.AbstractConstantEntityManager;
import com.b2c.prototype.service.manager.message.IMessageTypeManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class MessageTypeManager extends AbstractConstantEntityManager<ConstantPayloadDto, MessageType> implements IMessageTypeManager {
    public MessageTypeManager(IParameterFactory parameterFactory,
                              IEntityDao dao,
                              ITransformationFunctionService transformationFunctionService,
                              ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, MessageType.class),
                transformationFunctionService.getTransformationFunction(MessageType.class, ConstantPayloadDto.class)
        );
    }
}
