package com.b2c.prototype.service.processor.message.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.message.IMessageStatusService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class MessageStatusService extends AbstractOneFieldEntityService<MessageStatus> implements IMessageStatusService {
    public MessageStatusService(IParameterFactory parameterFactory,
                                IEntityDao dao,
                                ITransformationFunctionService transformationFunctionService,
                                ISingleValueMap singleValueMap) {
        super(parameterFactory, dao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneFieldEntityDto, MessageStatus> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, MessageStatus.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
