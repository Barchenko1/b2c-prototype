package com.b2c.prototype.processor.user.base;

import com.b2c.prototype.manager.message.IMessageTypeManager;
import com.b2c.prototype.modal.dto.payload.constant.MessageTypeDto;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.processor.user.IMessageTypeProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageTypeProcess implements IMessageTypeProcess {

    private final ObjectMapper objectMapper;
    private final IMessageTypeManager messageTypeManager;

    public MessageTypeProcess(ObjectMapper objectMapper,
                              IMessageTypeManager messageTypeManager) {
        this.objectMapper = objectMapper;
        this.messageTypeManager = messageTypeManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        MessageType entity = objectMapper.convertValue(payload, MessageType.class);
        messageTypeManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        MessageType entity = objectMapper.convertValue(payload, MessageType.class);
        messageTypeManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        messageTypeManager.removeEntity(value);
    }

    @Override
    public List<MessageTypeDto> getEntityList(String location) {
        return messageTypeManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, MessageTypeDto.class))
                .toList();
    }

    @Override
    public MessageTypeDto getEntity(String location, String value) {
        return Optional.of(messageTypeManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, MessageTypeDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
