package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.processor.option.IZoneOptionGroupProcess;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ZoneOptionGroupProcess implements IZoneOptionGroupProcess {
    private final IZoneOptionManager zoneOptionManager;
    private final IOrderTransformService orderTransformService;

    public ZoneOptionGroupProcess(IZoneOptionManager zoneOptionManager,
                                  IOrderTransformService orderTransformService) {
        this.zoneOptionManager = zoneOptionManager;
        this.orderTransformService = orderTransformService;
    }

    @Override
    public void persistEntity(ZoneOptionGroupDto zoneOptionGroupDto) {
        zoneOptionManager.persistEntity(zoneOptionGroupDto);
    }

    @Override
    public void mergeEntity(ZoneOptionGroupDto zoneOptionGroupDto, String value) {
        zoneOptionManager.mergeEntity(value, zoneOptionGroupDto);
    }

    @Override
    public void removeEntity(String value) {
        zoneOptionManager.removeEntity(value);
    }

    @Override
    public List<ZoneOptionGroupDto> getEntityList(String location) {
        return zoneOptionManager.getEntities().stream()
                .map(orderTransformService::mapZoneOptionGroupToZoneOptionGroupDto)
                .toList();
    }

    @Override
    public ZoneOptionGroupDto getEntity(String location, String value) {
        return Optional.of(zoneOptionManager.getEntity(value))
                .map(orderTransformService::mapZoneOptionGroupToZoneOptionGroupDto)
                .orElseThrow(() -> new RuntimeException(""));
    }

}
