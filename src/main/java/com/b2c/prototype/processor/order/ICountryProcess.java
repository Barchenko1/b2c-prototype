package com.b2c.prototype.processor.order;

import com.b2c.prototype.modal.dto.payload.constant.CountryDto;

import java.util.List;
import java.util.Map;

public interface ICountryProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<CountryDto> getEntityList(final String location);
    CountryDto getEntity(final String location, final String value);
}
