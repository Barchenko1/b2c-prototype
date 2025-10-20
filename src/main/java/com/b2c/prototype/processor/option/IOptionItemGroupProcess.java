package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;

import java.util.List;
import java.util.Map;

public interface IOptionItemGroupProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<OptionItemGroupDto> getEntityList(final String location);
    OptionItemGroupDto getEntity(final String location, final String value);
}
