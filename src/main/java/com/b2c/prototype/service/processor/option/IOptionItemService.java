package com.b2c.prototype.service.processor.option;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.request.OptionItemDto;
import com.b2c.prototype.modal.dto.searchfield.OptionItemSearchFieldEntityDto;

import java.util.List;

public interface IOptionItemService {
    void saveUpdateOptionItemSetByArticularId(OptionItemSearchFieldEntityDto optionItemSearchFieldEntityDto);
    void saveUpdateOptionItemSetByOptionGroupName(OptionItemDto optionItemDto);

    void deleteOptionItemByArticularId(OneFieldEntityDto oneFieldEntityDto);
    void deleteOptionItemByOptionGroupName(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);

    List<OptionItemDto> getOptionItemListByOptionGroup(OneFieldEntityDto oneFieldEntityDto);
    OptionItemDto getOptionItemByItemArticularId(OneFieldEntityDto oneFieldEntityDto);
    List<OptionItemDto> getOptionItemList();
}
