package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.constant.CountryPhoneCodeDto;

import java.util.List;
import java.util.Map;

public interface ICountryPhoneCodeProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<CountryPhoneCodeDto> getEntityList(final String location);
    CountryPhoneCodeDto getEntity(final String location, final String value);
}
