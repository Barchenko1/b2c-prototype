package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.item.IArticularStatusManager;
import com.b2c.prototype.modal.dto.payload.constant.ArticularStatusDto;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.processor.item.IArticularStatusProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ArticularStatusProcess implements IArticularStatusProcess {

    private final ObjectMapper objectMapper;
    private final IArticularStatusManager itemStatusManager;

    public ArticularStatusProcess(ObjectMapper objectMapper,
                                  IArticularStatusManager itemStatusManager) {
        this.objectMapper = objectMapper;
        this.itemStatusManager = itemStatusManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        ArticularStatus entity = objectMapper.convertValue(payload, ArticularStatus.class);
        itemStatusManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        ArticularStatus entity = objectMapper.convertValue(payload, ArticularStatus.class);
        itemStatusManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        itemStatusManager.removeEntity(value);
    }

    @Override
    public List<ArticularStatusDto> getEntityList(String location) {
        return itemStatusManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, ArticularStatusDto.class))
                .toList();
    }

    @Override
    public ArticularStatusDto getEntity(String location, String value) {
        return Optional.of(itemStatusManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, ArticularStatusDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
