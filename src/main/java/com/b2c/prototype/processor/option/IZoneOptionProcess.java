package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;

import java.util.List;

public interface IZoneOptionProcess {
    void persistEntity(final ZoneOptionDto zoneOptionDto);
    void mergeEntity(final ZoneOptionDto zoneOptionDto, final String value);
    void removeEntity(final String value);

    List<ZoneOptionDto> getEntityList(final String location);
    ZoneOptionDto getEntity(final String location, final String value);
}
