package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.IOptionItemGroupManager;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.processor.option.IOptionItemGroupProcess;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionItemGroupProcess implements IOptionItemGroupProcess {
    private final IOptionItemGroupManager optionGroupManager;
    private final IItemTransformService itemTransformService;

    public OptionItemGroupProcess(IOptionItemGroupManager optionGroupManager,
                                  IItemTransformService itemTransformService) {
        this.optionGroupManager = optionGroupManager;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void persistEntity(OptionItemGroupDto optionItemGroupDto) {
        optionGroupManager.persistEntity(optionItemGroupDto);
    }

    @Override
    public void mergeEntity(OptionItemGroupDto optionItemGroupDto, String value) {
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
