package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;

import java.util.List;

public interface ITimeDurationOptionGroupProcess {
    void persistEntity(final TimeDurationOptionGroupDto payload);
    void mergeEntity(final TimeDurationOptionGroupDto payload, final String value);
    void removeEntity(final String value);

    List<TimeDurationOptionGroupDto> getEntityList(final String location);
    TimeDurationOptionGroupDto getEntity(final String location, final String value);
}
