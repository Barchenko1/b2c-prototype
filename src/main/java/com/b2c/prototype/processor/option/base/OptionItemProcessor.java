package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.IOptionItemManager;
import com.b2c.prototype.modal.dto.payload.option.OptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.OptionItemDto;
import com.b2c.prototype.processor.option.IOptionItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OptionItemProcessor implements IOptionItemProcessor {

    private final IOptionItemManager optionItemManager;

    public OptionItemProcessor(IOptionItemManager optionItemManager) {
        this.optionItemManager = optionItemManager;
    }

    @Override
    public void saveUpdateOptionItem(Map<String, String> requestParams, OptionItemDto optionItemDto) {
        String articularId = requestParams.get("articularId");
        String optionGroupValue = requestParams.get("optionGroup");
        String optionItemValue = requestParams.get("optionItem");
        if (articularId != null) {
//            optionItemManager.saveUpdateOptionItemByArticularId(articularId, optionItemValue, optionGroupDto);
        }
        if (optionGroupValue != null) {
//            optionItemManager.saveUpdateOptionItemByOptionGroup(optionGroupValue, optionItemValue, optionGroupDto);
        }
    }

    @Override
    public void saveOptionItemSet(Map<String, String> requestParams, Set<OptionItemDto> optionItemDtoSet) {
//        optionItemDtoSet.forEach(optionItemDto -> {
//            optionItemManager.saveUpdateOptionItemByOptionGroup(optionItemDto);
//        });
    }

    @Override
    public void deleteOptionItem(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        String optionGroup = requestParams.get("optionGroup");
        String optionValue = requestParams.get("optionValue");
        if (articularId != null && optionValue != null) {
            optionItemManager.deleteOptionItemByArticularId(articularId, optionValue);
        }
        if (optionGroup != null && optionValue != null) {
            optionItemManager.deleteOptionItemByOptionGroup(optionGroup, optionValue);
        }
    }

    @Override
    public OptionItemDto getOptionItemDto(Map<String, String> requestParams) {
        String optionGroup = requestParams.get("optionGroup");
        if (optionGroup != null) {
//            return optionItemManager.getOptionItemListByOptionGroup(optionGroup);
        }
        throw new IllegalArgumentException("optionGroup cannot be null");
    }

    @Override
    public List<OptionItemDto> getOptionItemDtoList(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        if (articularId != null) {
            return optionItemManager.getOptionItemByItemArticularId(articularId);
        }
        return optionItemManager.getOptionItemList();
    }
}
