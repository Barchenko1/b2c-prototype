package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;

import java.util.List;
import java.util.Map;

public interface ITimeDurationOptionProcess {
    void persistEntity(TimeDurationOptionDto timeDurationOptionDto);
    void mergeEntity(Map<String, String> requestParams, TimeDurationOptionDto timeDurationOptionDto);
    void removeEntity(Map<String, String> requestParams);

    List<TimeDurationOptionDto> getEntities(Map<String, String> requestParams);
    TimeDurationOptionDto getEntity(Map<String, String> requestParams);
}
