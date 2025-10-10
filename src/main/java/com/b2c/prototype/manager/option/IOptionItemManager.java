package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.OptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.OptionItemDto;

import java.util.List;

public interface IOptionItemManager {
    void saveUpdateOptionItemByArticularId(String articularId, String optionItemValue, OptionItemDto optionItemDto);
    void saveUpdateOptionItemByOptionGroup(String optionGroupValue, String optionItemValue, OptionItemDto optionItemDto);

    void deleteOptionItemByArticularId(String articularId, String optionValue);
    void deleteOptionItemByOptionGroup(String optionGroup, String optionValue);

    List<OptionItemDto> getOptionItemByItemArticularId(String articularId);
    List<OptionItemDto> getOptionItemListByOptionGroup(String optionGroup);
    List<OptionItemDto> getOptionItemList();
}
