package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.ZoneOptionDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.ZoneOptionGroup;
import com.b2c.prototype.processor.option.IZoneOptionProcess;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ZoneOptionProcess implements IZoneOptionProcess {

    private final ObjectMapper objectMapper;
    private final IZoneOptionManager zoneOptionManager;
    private final IOrderTransformService orderTransformService;

    public ZoneOptionProcess(ObjectMapper objectMapper,
                             IZoneOptionManager zoneOptionManager,
                             IOrderTransformService orderTransformService) {
        this.objectMapper = objectMapper;
        this.zoneOptionManager = zoneOptionManager;
        this.orderTransformService = orderTransformService;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        ZoneOptionGroup entity = objectMapper.convertValue(payload, ZoneOptionGroup.class);
        zoneOptionManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        ZoneOptionGroupDto zoneOptionGroupDto = objectMapper.convertValue(payload, ZoneOptionGroupDto.class);
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
