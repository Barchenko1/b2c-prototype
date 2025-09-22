package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.item.IItemTypeManager;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.processor.item.IItemTypeProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemTypeProcess implements IItemTypeProcess {

    private final ObjectMapper objectMapper;
    private final IItemTypeManager itemTypeManager;

    public ItemTypeProcess(ObjectMapper objectMapper, IItemTypeManager itemTypeManager) {
        this.objectMapper = objectMapper;
        this.itemTypeManager = itemTypeManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        ItemType entity = objectMapper.convertValue(payload, ItemType.class);
        itemTypeManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        ItemType entity = objectMapper.convertValue(payload, ItemType.class);
        itemTypeManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        itemTypeManager.removeEntity(value);
    }

    @Override
    public List<ItemTypeDto> getEntityList(String location) {
        return itemTypeManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, ItemTypeDto.class))
                .toList();
    }

    @Override
    public ItemTypeDto getEntity(String location, String value) {
        return Optional.of(itemTypeManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, ItemTypeDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
