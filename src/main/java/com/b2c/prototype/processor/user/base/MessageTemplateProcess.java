package com.b2c.prototype.processor.user.base;

import com.b2c.prototype.manager.message.IMessageTemplateManager;
import com.b2c.prototype.modal.dto.payload.message.MessageTemplateDto;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.b2c.prototype.processor.user.IMessageTemplateProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageTemplateProcess implements IMessageTemplateProcess {
    private final ObjectMapper objectMapper;
    private final IMessageTemplateManager messageTemplateManager;

    public MessageTemplateProcess(ObjectMapper objectMapper,
                                  IMessageTemplateManager messageTemplateManager) {
        this.objectMapper = objectMapper;
        this.messageTemplateManager = messageTemplateManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        MessageTemplate entity = objectMapper.convertValue(payload, MessageTemplate.class);
        messageTemplateManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        MessageTemplate entity = objectMapper.convertValue(payload, MessageTemplate.class);
        messageTemplateManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        messageTemplateManager.removeEntity(value);
    }

    @Override
    public List<MessageTemplateDto> getEntityList(String location) {
        return  messageTemplateManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, MessageTemplateDto.class))
                .toList();
    }

    @Override
    public MessageTemplateDto getEntity(String location, String value) {
        return Optional.of(messageTemplateManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, MessageTemplateDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
