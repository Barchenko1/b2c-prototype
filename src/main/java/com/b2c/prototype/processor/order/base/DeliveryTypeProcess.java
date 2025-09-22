package com.b2c.prototype.processor.order.base;

import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.b2c.prototype.modal.dto.payload.order.DeliveryTypeDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.processor.order.IDeliveryTypeProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DeliveryTypeProcess implements IDeliveryTypeProcess {
    private final ObjectMapper objectMapper;
    private final IDeliveryTypeManager deliveryTypeManager;

    public DeliveryTypeProcess(ObjectMapper objectMapper, IDeliveryTypeManager deliveryTypeManager) {
        this.objectMapper = objectMapper;
        this.deliveryTypeManager = deliveryTypeManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        DeliveryType entity = objectMapper.convertValue(payload, DeliveryType.class);
        deliveryTypeManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        DeliveryType entity = objectMapper.convertValue(payload, DeliveryType.class);
        deliveryTypeManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        deliveryTypeManager.removeEntity(value);
    }

    @Override
    public List<DeliveryTypeDto> getEntityList(String location) {
        return deliveryTypeManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, DeliveryTypeDto.class))
                .toList();
    }

    @Override
    public DeliveryTypeDto getEntity(String location, String value) {
        return Optional.of(deliveryTypeManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, DeliveryTypeDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
