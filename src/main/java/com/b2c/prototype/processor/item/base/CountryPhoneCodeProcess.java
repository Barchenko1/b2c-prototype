package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.userdetails.ICountryPhoneCodeManager;
import com.b2c.prototype.modal.dto.payload.constant.CountryPhoneCodeDto;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.processor.item.ICountryPhoneCodeProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CountryPhoneCodeProcess implements ICountryPhoneCodeProcess {
    private final ObjectMapper objectMapper;
    private final ICountryPhoneCodeManager countryPhoneCodeManager;

    public CountryPhoneCodeProcess(ObjectMapper objectMapper, ICountryPhoneCodeManager countryPhoneCodeManager) {
        this.objectMapper = objectMapper;
        this.countryPhoneCodeManager = countryPhoneCodeManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        CountryPhoneCode entity = objectMapper.convertValue(payload, CountryPhoneCode.class);
        countryPhoneCodeManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        CountryPhoneCode entity = objectMapper.convertValue(payload, CountryPhoneCode.class);
        countryPhoneCodeManager.mergeEntity(decodeCountryCode(value), entity);
    }

    @Override
    public void removeEntity(String value) {
        countryPhoneCodeManager.removeEntity(decodeCountryCode(value));
    }

    @Override
    public List<CountryPhoneCodeDto> getEntityList(String location) {
        return countryPhoneCodeManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, CountryPhoneCodeDto.class))
                .toList();
    }

    @Override
    public CountryPhoneCodeDto getEntity(String location, String value) {
        return Optional.of(countryPhoneCodeManager.getEntity(decodeCountryCode(value)))
                .map(entity -> objectMapper.convertValue(entity, CountryPhoneCodeDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    private String decodeCountryCode(String countryCode) {
        return UriUtils.decode(countryCode, StandardCharsets.UTF_8);
    }
}
