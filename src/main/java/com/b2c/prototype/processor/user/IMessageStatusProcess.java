package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.constant.MessageStatusDto;

import java.util.List;
import java.util.Map;

public interface IMessageStatusProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<MessageStatusDto> getEntityList(final String location);
    MessageStatusDto getEntity(final String location, final String value);
}
