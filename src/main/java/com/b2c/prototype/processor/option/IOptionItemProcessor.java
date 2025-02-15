package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IOptionItemProcessor {
    void saveUpdateOptionItem(Map<String, String> requestParams, SingleOptionItemDto optionItemDto);
    void saveOptionItemSet(Map<String, String> requestParams, Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDto);

    void deleteOptionItem(Map<String, String> requestParams);

    List<OptionGroupOptionItemSetDto> getOptionItemDtoList(Map<String, String> requestParams);
    OptionGroupOptionItemSetDto getOptionItemDto(Map<String, String> requestParams);

}
