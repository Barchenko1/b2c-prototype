package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.item.IBrandManager;
import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.processor.item.IBrandProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BrandProcess implements IBrandProcess {

    private final ObjectMapper objectMapper;
    private final IBrandManager brandManager;

    public BrandProcess(ObjectMapper objectMapper,
                       IBrandManager brandManager) {
        this.objectMapper = objectMapper;
        this.brandManager = brandManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        Brand entity = objectMapper.convertValue(payload, Brand.class);
        brandManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        Brand entity = objectMapper.convertValue(payload, Brand.class);
        brandManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        brandManager.removeEntity(value);
    }

    @Override
    public List<BrandDto> getEntityList(String location) {
        return brandManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, BrandDto.class))
                .toList();
    }

    @Override
    public BrandDto getEntity(String location, String value) {
        return Optional.of(brandManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, BrandDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
