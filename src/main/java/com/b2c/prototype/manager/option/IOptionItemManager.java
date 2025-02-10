package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;

import java.util.List;

public interface IOptionItemManager {
    void saveUpdateOptionItemByArticularId(String articularId, String optionItemValue, SingleOptionItemDto singleOptionItemDto);
    void saveUpdateOptionItemByOptionGroup(String optionGroupValue, String optionItemValue, SingleOptionItemDto singleOptionItemDto);
    void saveOptionItemSet(List<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList);

    void deleteOptionItemByArticularId(String articularId, String optionValue);
    void deleteOptionItemByOptionGroup(String optionGroup, String optionValue);

    List<OptionGroupOptionItemSetDto> getOptionItemByItemArticularId(String articularId);
    OptionGroupOptionItemSetDto getOptionItemListByOptionGroup(String optionGroup);
    List<OptionGroupOptionItemSetDto> getOptionItemList();
}
