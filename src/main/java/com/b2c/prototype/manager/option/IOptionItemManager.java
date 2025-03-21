package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.option.SingleOptionItemDto;

import java.util.List;
import java.util.Set;

public interface IOptionItemManager {
    void saveUpdateOptionItemByArticularId(String articularId, String optionItemValue, SingleOptionItemDto singleOptionItemDto);
    void saveUpdateOptionItemByOptionGroup(String optionGroupValue, String optionItemValue, SingleOptionItemDto singleOptionItemDto);
    void saveOptionItemSet(Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList);

    void deleteOptionItemByArticularId(String articularId, String optionValue);
    void deleteOptionItemByOptionGroup(String optionGroup, String optionValue);

    List<OptionGroupOptionItemSetDto> getOptionItemByItemArticularId(String articularId);
    OptionGroupOptionItemSetDto getOptionItemListByOptionGroup(String optionGroup);
    List<OptionGroupOptionItemSetDto> getOptionItemList();
}
