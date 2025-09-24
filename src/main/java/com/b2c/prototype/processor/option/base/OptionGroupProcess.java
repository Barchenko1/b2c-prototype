package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.b2c.prototype.modal.dto.payload.option.OptionGroupDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.processor.option.IOptionGroupProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OptionGroupProcess implements IOptionGroupProcess {
    private final ObjectMapper objectMapper;
    private final IOptionGroupManager optionGroupManager;

    public OptionGroupProcess(ObjectMapper objectMapper, IOptionGroupManager optionGroupManager) {
        this.objectMapper = objectMapper;
        this.optionGroupManager = optionGroupManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        OptionGroup entity = objectMapper.convertValue(payload, OptionGroup.class);
        optionGroupManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        OptionGroup entity = objectMapper.convertValue(payload, OptionGroup.class);
        optionGroupManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        optionGroupManager.removeEntity(value);
    }

    @Override
    public List<OptionGroupDto> getEntityList(String location) {
        return optionGroupManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, OptionGroupDto.class))
                .toList();
    }

    @Override
    public OptionGroupDto getEntity(String location, String value) {
        return Optional.of(optionGroupManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, OptionGroupDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
