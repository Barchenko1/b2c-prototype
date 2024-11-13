package com.b2c.prototype.service.processor.message.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.message.IMessageStatusService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class MessageStatusService extends AbstractOneFieldEntityService<MessageStatus> implements IMessageStatusService {
    public MessageStatusService(IParameterFactory parameterFactory,
                                ISingleEntityDao dao,
                                IEntityCachedMap entityCachedMap) {
        super(parameterFactory, dao, entityCachedMap);
    }

    @Override
    protected Function<OneFieldEntityDto, MessageStatus> getFunction() {
        return oneFieldEntityDto -> MessageStatus.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
