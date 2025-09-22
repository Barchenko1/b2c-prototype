package com.b2c.prototype.processor.user.base;

import com.b2c.prototype.manager.message.IMessageStatusManager;
import com.b2c.prototype.modal.dto.payload.constant.MessageStatusDto;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.processor.user.IMessageStatusProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageStatusProcess implements IMessageStatusProcess {

    private final ObjectMapper objectMapper;
    private final IMessageStatusManager messageStatusManager;

    public MessageStatusProcess(ObjectMapper objectMapper,
                                IMessageStatusManager messageStatusManager) {
        this.objectMapper = objectMapper;
        this.messageStatusManager = messageStatusManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        MessageStatus entity = objectMapper.convertValue(payload, MessageStatus.class);
        messageStatusManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        MessageStatus entity = objectMapper.convertValue(payload, MessageStatus.class);
        messageStatusManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        messageStatusManager.removeEntity(value);
    }

    @Override
    public List<MessageStatusDto> getEntityList(String location) {
        return  messageStatusManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, MessageStatusDto.class))
                .toList();
    }

    @Override
    public MessageStatusDto getEntity(String location, String value) {
        return Optional.of(messageStatusManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, MessageStatusDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
