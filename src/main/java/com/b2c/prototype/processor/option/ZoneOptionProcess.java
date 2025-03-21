package com.b2c.prototype.processor.option;

import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;

import java.util.List;
import java.util.Map;

public class ZoneOptionProcess implements IZoneOptionProcess {

    private final IZoneOptionManager zoneOptionManager;

    public ZoneOptionProcess(IZoneOptionManager zoneOptionManager) {
        this.zoneOptionManager = zoneOptionManager;
    }

    @Override
    public void saveUpdateZoneOption(Map<String, String> requestParams, ZoneOptionDto zoneOptionDto) {
        String value = requestParams.get("value");
        zoneOptionManager.saveUpdateZoneOption(value, zoneOptionDto);
    }

    @Override
    public void deleteZoneOption(Map<String, String> requestParams) {
        String value = requestParams.get("value");
        zoneOptionManager.deleteZoneOption(value);
    }

    @Override
    public List<ZoneOptionDto> getZoneOptionList(Map<String, String> requestParams) {
        return zoneOptionManager.getZoneOptionDtoList();
    }

    @Override
    public ZoneOptionDto getZoneOption(Map<String, String> requestParams) {
        String value = requestParams.get("value");
        return zoneOptionManager.getZoneOptionDto(value);
    }

}
