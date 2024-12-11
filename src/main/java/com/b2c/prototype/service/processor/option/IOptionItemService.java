package com.b2c.prototype.service.processor.option;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.OptionItemDtoDelete;
import com.b2c.prototype.modal.dto.request.OptionItemDto;
import com.b2c.prototype.modal.dto.update.OptionItemDtoUpdate;

import java.util.List;

public interface IOptionItemService {
    void saveOptionItemSet(OptionItemDto optionItemDto);
    void updateOptionItemByArticularId(OptionItemDtoUpdate optionItemDtoUpdate);
    void updateOptionItemByOptionGroupName(OptionItemDtoUpdate optionItemDtoUpdate);
    void deleteOptionItemByArticularId(OptionItemDtoDelete optionItemDtoDelete);
    void deleteOptionItemByOptionGroupName(OptionItemDtoDelete optionItemDtoDelete);

    List<String> getOptionItemListByOptionGroup(String optionGroupName);
    List<OptionItemDto> getOptionItems();
}
