package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.OptionGroupDto;

import java.util.List;
import java.util.Map;

public interface IOptionGroupProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<OptionGroupDto> getEntityList(final String location);
    OptionGroupDto getEntity(final String location, final String value);
}
