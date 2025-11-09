package com.b2c.prototype.processor.region;

import com.b2c.prototype.modal.dto.payload.region.RegionDto;

import java.util.List;

public interface IRegionProcess {
    void persistEntity(final RegionDto regionDto);
    void mergeEntity(final String code, final RegionDto regionDto);
    void removeEntity(final String code);

    List<RegionDto> getEntityList();
    RegionDto getEntity(final String code);
}
