package com.b2c.prototype.service.processor.option;

import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.request.OptionItemDto;
import com.b2c.prototype.modal.dto.update.OptionItemDtoUpdate;

import java.util.List;

public interface IOptionItemService {
    void saveUpdateOptionItemSetByArticularId(OptionItemDtoUpdate optionItemDtoUpdate);
    void saveUpdateOptionItemSetByOptionGroupName(OptionItemDtoUpdate optionItemDtoUpdate);

    void replaceOptionItemSetByArticularId(OptionItemDtoUpdate optionItemDtoUpdate);
    void replaceOptionItemSetByOptionGroupName(OptionItemDtoUpdate optionItemDtoUpdate);
    void deleteOptionItemByArticularId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);
    void deleteOptionItemByOptionGroupName(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);

    List<String> getOptionItemListByOptionGroup(String optionGroupName);
    List<OptionItemDto> getOptionItems();
}
