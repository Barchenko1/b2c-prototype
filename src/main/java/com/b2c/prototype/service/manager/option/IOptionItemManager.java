package com.b2c.prototype.service.manager.option;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.searchfield.OptionItemSearchFieldEntityDto;

import java.util.List;

public interface IOptionItemManager {
    void saveUpdateOptionItemSetByArticularId(OptionItemSearchFieldEntityDto optionItemSearchFieldEntityDto);
    void saveUpdateOptionItemSetByOptionGroupName(OptionItemDto optionItemDto);

    void deleteOptionItemByArticularId(OneFieldEntityDto oneFieldEntityDto);
    void deleteOptionItemByOptionGroupName(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);

    List<OptionItemDto> getOptionItemListByOptionGroup(OneFieldEntityDto oneFieldEntityDto);
    OptionItemDto getOptionItemByItemArticularId(OneFieldEntityDto oneFieldEntityDto);
    List<OptionItemDto> getOptionItemList();
}
