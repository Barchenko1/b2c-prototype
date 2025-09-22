package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.message.MessageTemplateDto;

import java.util.List;
import java.util.Map;

public interface IMessageTemplateProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<MessageTemplateDto> getEntityList(final String location);
    MessageTemplateDto getEntity(final String location, final String value);
}
