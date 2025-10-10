package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ITimeDurationOptionManager {
    void persistEntity(TimeDurationOptionDto timeDurationOptionDto);
    void mergeEntity(String value, TimeDurationOptionDto timeDurationOptionDto);
    void removeEntity(String value);
    TimeDurationOptionDto getEntity(String value);
    List<TimeDurationOptionDto> getEntities();
}
