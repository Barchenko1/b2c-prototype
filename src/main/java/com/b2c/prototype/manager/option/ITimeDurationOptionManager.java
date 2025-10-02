package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ITimeDurationOptionManager {
    void persistEntity(TimeDurationOptionDto timeDurationOptionDto);
    void mergeEntity(LocalDateTime start, LocalDateTime end, TimeDurationOptionDto timeDurationOptionDto);
    void removeEntity(LocalDateTime start, LocalDateTime end);
    TimeDurationOptionDto getEntity(LocalDateTime start, LocalDateTime end);
    List<TimeDurationOptionDto> getEntities();
}
