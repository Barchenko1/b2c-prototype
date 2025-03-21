package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;

import java.util.List;

public interface IZoneOptionManager {
    void saveUpdateZoneOption(String zoneValue, ZoneOptionDto zoneOptionDto);
    void deleteZoneOption(String zoneValue);
    ZoneOptionDto getZoneOptionDto(String zoneValue);
    List<ZoneOptionDto> getZoneOptionDtoList();
}
