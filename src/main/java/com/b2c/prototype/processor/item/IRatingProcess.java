package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.constant.RatingDto;

import java.util.List;
import java.util.Map;

public interface IRatingProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<RatingDto> getEntityList(final String location);
    RatingDto getEntity(final String location, final String value);
}
