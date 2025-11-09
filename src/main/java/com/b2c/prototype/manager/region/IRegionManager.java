package com.b2c.prototype.manager.region;

import com.b2c.prototype.modal.dto.payload.region.RegionDto;
import com.b2c.prototype.modal.entity.region.Region;

import java.util.List;

public interface IRegionManager {
    void persistEntity(final RegionDto regionDto);
    void mergeEntity(final String code, RegionDto regionDto);
    void removeEntity(final String code);

    List<Region> getEntityList();
    Region getEntity(final String code);
}
