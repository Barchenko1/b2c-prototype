package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;
import com.b2c.prototype.modal.dto.payload.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseTimeDurationOptionDto;

import java.util.List;
import java.util.Map;

public interface ITimeDurationOptionProcess {
    void saveUpdateTimeDurationOption(Map<String, String> requestParams, TimeDurationOptionDto timeDurationOptionDto);
    void deleteTimeDurationOption(Map<String, String> requestParams);

    List<ResponseTimeDurationOptionDto> getTimeDurationOptionDtoList(Map<String, String> requestParams);
    ResponseTimeDurationOptionDto getTimeDurationOptionDto(Map<String, String> requestParams);
}
