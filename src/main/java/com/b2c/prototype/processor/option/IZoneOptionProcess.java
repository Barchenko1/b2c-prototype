package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;

import java.util.List;
import java.util.Map;

public interface IZoneOptionProcess {
    void saveUpdateZoneOption(Map<String, String> requestParams, ZoneOptionDto zoneOptionDto);
    void deleteZoneOption(Map<String, String> requestParams);

    List<ZoneOptionDto> getZoneOptionList(Map<String, String> requestParams);
    ZoneOptionDto getZoneOption(Map<String, String> requestParams);
}
