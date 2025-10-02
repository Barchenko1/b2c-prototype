package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.processor.option.IZoneOptionProcess;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneOptionProcess implements IZoneOptionProcess {

    private final IZoneOptionManager zoneOptionManager;

    public ZoneOptionProcess(IZoneOptionManager zoneOptionManager) {
        this.zoneOptionManager = zoneOptionManager;
    }

    @Override
    public void persistEntity(ZoneOptionDto zoneOptionDto) {
        zoneOptionManager.persistEntity(zoneOptionDto);
    }

    @Override
    public void mergeEntity(ZoneOptionDto zoneOptionDto, String value) {
        zoneOptionManager.mergeEntity(value, zoneOptionDto);
    }

    @Override
    public void removeEntity(String value) {
        zoneOptionManager.removeEntity(value);
    }

    @Override
    public List<ZoneOptionDto> getEntityList(String location) {
        return zoneOptionManager.getEntities();
    }

    @Override
    public ZoneOptionDto getEntity(String location, String value) {
        return zoneOptionManager.getEntity(value);
    }

}
