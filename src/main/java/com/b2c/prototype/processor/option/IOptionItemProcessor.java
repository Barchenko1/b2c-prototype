package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;

import java.util.List;
import java.util.Map;

public interface IOptionItemProcessor {
    void saveUpdateOptionItem(SingleOptionItemDto optionItemDto, Map<String, String> requestParams);
    void saveOptionItemSet(List<OptionItemDto> optionItemDto, Map<String, String> requestParams);

    void deleteOptionItem(Map<String, String> requestParams);

    List<OptionItemDto> getOptionItemDtoList(Map<String, String> requestParams);
    OptionItemDto getOptionItemDto(Map<String, String> requestParams);

}
