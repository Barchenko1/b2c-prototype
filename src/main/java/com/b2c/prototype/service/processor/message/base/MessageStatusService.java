package com.b2c.prototype.service.processor.message.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.message.IMessageStatusService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class MessageStatusService extends AbstractConstantEntityService<ConstantPayloadDto, MessageStatus> implements IMessageStatusService {
    public MessageStatusService(IParameterFactory parameterFactory,
                                IEntityDao dao,
                                ITransformationFunctionService transformationFunctionService,
                                ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, MessageStatus.class),
                transformationFunctionService.getTransformationFunction(MessageStatus.class, ConstantPayloadDto.class));
    }
}
