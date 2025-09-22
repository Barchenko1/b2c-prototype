package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.constant.MessageTypeDto;

import java.util.List;
import java.util.Map;

public interface IMessageTypeProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<MessageTypeDto> getEntityList(final String location);
    MessageTypeDto getEntity(final String location, final String value);
}
