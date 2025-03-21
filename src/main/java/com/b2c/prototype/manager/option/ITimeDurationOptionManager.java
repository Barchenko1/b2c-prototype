package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseTimeDurationOptionDto;

import java.util.List;

public interface ITimeDurationOptionManager {
    void saveUpdateTimeDurationOption(String timeDurationValue, TimeDurationOptionDto timeDurationOptionDto);
    void deleteTimeDurationOption(String timeDurationValue);
    ResponseTimeDurationOptionDto getTimeDurationOptionDto(String timeDurationValue);
    List<ResponseTimeDurationOptionDto> getTimeDurationOptionDtoList();
}
