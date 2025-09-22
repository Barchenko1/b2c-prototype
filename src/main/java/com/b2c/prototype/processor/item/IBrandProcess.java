package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.constant.BrandDto;

import java.util.List;
import java.util.Map;

public interface IBrandProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<BrandDto> getEntityList(final String location);
    BrandDto getEntity(final String location, final String value);

}
