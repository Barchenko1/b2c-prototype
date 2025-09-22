package com.b2c.prototype.processor.order.base;

import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.b2c.prototype.modal.dto.payload.order.OrderStatusDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.processor.order.IOrderStatusProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderStatusProcessor implements IOrderStatusProcessor {
    private final ObjectMapper objectMapper;
    private final IOrderStatusManager orderStatusManager;

    public OrderStatusProcessor(ObjectMapper objectMapper, IOrderStatusManager orderStatusManager) {
        this.objectMapper = objectMapper;
        this.orderStatusManager = orderStatusManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        OrderStatus entity = objectMapper.convertValue(payload, OrderStatus.class);
        orderStatusManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        OrderStatus entity = objectMapper.convertValue(payload, OrderStatus.class);
        orderStatusManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        orderStatusManager.removeEntity(value);
    }

    @Override
    public List<OrderStatusDto> getEntityList(String location) {
        return orderStatusManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, OrderStatusDto.class))
                .toList();
    }

    @Override
    public OrderStatusDto getEntity(String location, String value) {
        return Optional.of(orderStatusManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, OrderStatusDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
