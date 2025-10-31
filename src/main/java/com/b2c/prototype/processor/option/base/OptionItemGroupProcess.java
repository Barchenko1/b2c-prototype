package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.IOptionItemGroupManager;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.processor.option.IOptionItemGroupProcess;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OptionItemGroupProcess implements IOptionItemGroupProcess {
    private final ObjectMapper objectMapper;
    private final IOptionItemGroupManager optionGroupManager;
    private final IItemTransformService itemTransformService;

    public OptionItemGroupProcess(ObjectMapper objectMapper,
                                  IOptionItemGroupManager optionGroupManager,
                                  IItemTransformService itemTransformService) {
        this.objectMapper = objectMapper;
        this.optionGroupManager = optionGroupManager;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        OptionItemGroupDto optionItemGroupDto = objectMapper.convertValue(payload, OptionItemGroupDto.class);
        optionGroupManager.persistEntity(optionItemGroupDto);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        OptionItemGroupDto optionItemGroupDto = objectMapper.convertValue(payload, OptionItemGroupDto.class);
        optionGroupManager.mergeEntity(value, optionItemGroupDto);
    }

    @Override
    public void removeEntity(String value) {
        optionGroupManager.removeEntity(value);
    }

    @Override
    public List<OptionItemGroupDto> getEntityList(String location) {
        return optionGroupManager.getEntities().stream()
                .map(itemTransformService::mapOptionGroupToOptionItemGroupDto)
                .toList();
    }

    @Override
    public OptionItemGroupDto getEntity(String location, String value) {
        return Optional.of(optionGroupManager.getEntity(value))
                .map(itemTransformService::mapOptionGroupToOptionItemGroupDto)
                .orElseThrow(() -> new RuntimeException(""));
    }
}
