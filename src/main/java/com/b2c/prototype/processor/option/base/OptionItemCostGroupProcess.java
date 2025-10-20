package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.IOptionItemCostGroupManager;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.processor.option.IOptionItemCostGroupProcess;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OptionItemCostGroupProcess implements IOptionItemCostGroupProcess {
    private final ObjectMapper objectMapper;
    private final IOptionItemCostGroupManager optionItemCostManager;
    private final IItemTransformService itemTransformService;

    public OptionItemCostGroupProcess(ObjectMapper objectMapper,
                                      IOptionItemCostGroupManager optionItemCostManager,
                                      IItemTransformService itemTransformService) {
        this.objectMapper = objectMapper;
        this.optionItemCostManager = optionItemCostManager;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        OptionItemCostGroupDto dto = objectMapper.convertValue(payload, OptionItemCostGroupDto.class);
        optionItemCostManager.persistEntity(dto);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        OptionItemCostGroupDto dto = objectMapper.convertValue(payload, OptionItemCostGroupDto.class);
        optionItemCostManager.mergeEntity(value, dto);
    }

    @Override
    public void removeEntity(String value) {
        optionItemCostManager.removeEntity(value);
    }

    @Override
    public List<OptionItemCostGroupDto> getEntityList(String location) {
        return optionItemCostManager.getEntities().stream()
                .map(itemTransformService::mapOptionGroupToOptionItemCostGroupDto)
                .toList();
    }

    @Override
    public OptionItemCostGroupDto getEntity(String location, String value) {
        return Optional.of(optionItemCostManager.getEntity(value))
                .map(itemTransformService::mapOptionGroupToOptionItemCostGroupDto)
                .orElseThrow(() -> new RuntimeException(""));
    }


}
