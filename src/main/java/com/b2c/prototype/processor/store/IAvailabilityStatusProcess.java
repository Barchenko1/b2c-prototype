package com.b2c.prototype.processor.store;

import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;

import java.util.List;
import java.util.Map;

public interface IAvailabilityStatusProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<AvailabilityStatusDto> getEntityList(final String location);
    AvailabilityStatusDto getEntity(final String location, final String value);
}
