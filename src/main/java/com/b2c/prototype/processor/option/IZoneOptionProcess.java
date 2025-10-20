package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;

import java.util.List;
import java.util.Map;

public interface IZoneOptionProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<ZoneOptionGroupDto> getEntityList(final String location);
    ZoneOptionGroupDto getEntity(final String location, final String value);
}
