package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.ZoneOptionGroup;

import java.util.List;
import java.util.Optional;

public interface IZoneOptionManager {
    void persistEntity(ZoneOptionGroup zoneOptionGroup);
    void mergeEntity(String value, ZoneOptionGroupDto zoneOptionGroupDto);
    void removeEntity(String value);

    ZoneOptionGroup getEntity(String value);
    Optional<ZoneOptionGroup> getEntityOptional(String value);
    List<ZoneOptionGroup> getEntities();

}
