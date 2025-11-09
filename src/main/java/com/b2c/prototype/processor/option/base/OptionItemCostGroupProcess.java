package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.IOptionItemCostGroupManager;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.processor.option.IOptionItemCostGroupProcess;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OptionItemCostGroupProcess implements IOptionItemCostGroupProcess {
    private final IOptionItemCostGroupManager optionItemCostManager;
    private final IItemTransformService itemTransformService;

    public OptionItemCostGroupProcess(IOptionItemCostGroupManager optionItemCostManager,
                                      IItemTransformService itemTransformService) {
        this.optionItemCostManager = optionItemCostManager;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void persistEntity(OptionItemCostGroupDto payload) {
        optionItemCostManager.persistEntity(payload);
    }

    @Override
    public void mergeEntity(OptionItemCostGroupDto payload, String value) {
        optionItemCostManager.mergeEntity(value, payload);
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
