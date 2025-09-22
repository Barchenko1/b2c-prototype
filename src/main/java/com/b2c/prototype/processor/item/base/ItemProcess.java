package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.processor.item.IItemProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemProcess implements IItemProcess {

    private final ObjectMapper objectMapper;

    public ItemProcess(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {

    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {

    }

    @Override
    public void removeEntity(String value) {

    }

    @Override
    public List<BrandDto> getEntityList(String location) {
        return List.of();
    }

    @Override
    public BrandDto getEntity(String location, String value) {
        return null;
    }
}
