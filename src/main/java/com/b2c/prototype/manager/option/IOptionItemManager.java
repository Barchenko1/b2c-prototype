package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;

import java.util.List;

public interface IOptionItemManager {
    void saveUpdateOptionItemByArticularId(String articularId, String optionItemValue, SingleOptionItemDto singleOptionItemDto);
    void saveUpdateOptionItemByOptionGroup(String optionGroupValue, String optionItemValue, SingleOptionItemDto singleOptionItemDto);
    void saveOptionItemSet(List<OptionItemDto> optionItemDtoList);

    void deleteOptionItemByArticularId(String articularId, String optionValue);
    void deleteOptionItemByOptionGroup(String optionGroup, String optionValue);

    List<OptionItemDto> getOptionItemByItemArticularId(String articularId);
    OptionItemDto getOptionItemListByOptionGroup(String optionGroup);
    List<OptionItemDto> getOptionItemList();
}
