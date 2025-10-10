package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.OptionItemDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IOptionItemProcessor {
    void saveUpdateOptionItem(Map<String, String> requestParams, OptionItemDto optionItemDto);
    void saveOptionItemSet(Map<String, String> requestParams, Set<OptionItemDto> optionItemDtoSet);

    void deleteOptionItem(Map<String, String> requestParams);

    List<OptionItemDto> getOptionItemDtoList(Map<String, String> requestParams);
    OptionItemDto getOptionItemDto(Map<String, String> requestParams);

}
