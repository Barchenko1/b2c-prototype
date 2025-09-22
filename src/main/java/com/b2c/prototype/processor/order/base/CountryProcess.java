package com.b2c.prototype.processor.order.base;

import com.b2c.prototype.manager.address.ICountryManager;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.processor.order.ICountryProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CountryProcess implements ICountryProcess {
    private final ObjectMapper objectMapper;
    private final ICountryManager countryManager;

    public CountryProcess(ObjectMapper objectMapper, ICountryManager countryManager) {
        this.objectMapper = objectMapper;
        this.countryManager = countryManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        Country entity = objectMapper.convertValue(payload, Country.class);
        countryManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        Country entity = objectMapper.convertValue(payload, Country.class);
        countryManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        countryManager.removeEntity(value);
    }

    @Override
    public List<CountryDto> getEntityList(String location) {
        return countryManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, CountryDto.class))
                .toList();
    }

    @Override
    public CountryDto getEntity(String location, String value) {
        return Optional.of(countryManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, CountryDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
