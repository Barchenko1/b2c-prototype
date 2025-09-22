package com.b2c.prototype.processor.store.base;

import com.b2c.prototype.manager.store.IAvailabilityStatusManager;
import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.b2c.prototype.processor.store.IAvailabilityStatusProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AvailabilityStatusProcess implements IAvailabilityStatusProcess {
    private final ObjectMapper objectMapper;
    private final IAvailabilityStatusManager availabilityStatusManager;

    public AvailabilityStatusProcess(ObjectMapper objectMapper, IAvailabilityStatusManager availabilityStatusManager) {
        this.objectMapper = objectMapper;
        this.availabilityStatusManager = availabilityStatusManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        AvailabilityStatus entity = objectMapper.convertValue(payload, AvailabilityStatus.class);
        availabilityStatusManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        AvailabilityStatus entity = objectMapper.convertValue(payload, AvailabilityStatus.class);
        availabilityStatusManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        availabilityStatusManager.removeEntity(value);
    }

    @Override
    public List<AvailabilityStatusDto> getEntityList(String location) {
        return availabilityStatusManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, AvailabilityStatusDto.class))
                .toList();
    }

    @Override
    public AvailabilityStatusDto getEntity(String location, String value) {
        return Optional.of(availabilityStatusManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, AvailabilityStatusDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
