package com.b2c.prototype.processor.constant;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.b2c.prototype.util.Constant.COUNTRY_SERVICE_ID;
import static com.b2c.prototype.util.Constant.RATING_SERVICE_ID;

@Service
public class ConstantProcessorService implements IConstantProcessorService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IGeneralEntityDao generalEntityDao;

    public ConstantProcessorService(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;

    }

    public void saveConstantEntity(final Map<String, Object> payload,
                                   final String serviceId) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            CountryDto countryDto = objectMapper.convertValue(payload, CountryDto.class);
            generalEntityDao.persistEntity(countryDto);
        }
        if (serviceId.equals(RATING_SERVICE_ID)) {
            generalEntityDao.persistEntity((NumberConstantPayloadDto) payload);
        }
        else {
            ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
            generalEntityDao.persistEntity(constantPayloadDto);
        }
    }

    public void putConstantEntity(final Map<String, Object> payload,
                                  final String serviceId,
                                  final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            CountryDto countryDto = objectMapper.convertValue(payload, CountryDto.class);
            generalEntityDao.mergeEntity(countryDto);
        }
        else {
            ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
            generalEntityDao.mergeEntity(constantPayloadDto);
        }
    }

    public void patchConstantEntity(final Map<String, Object> payload,
                                    final String serviceId,
                                    final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            CountryDto countryDto = objectMapper.convertValue(payload, CountryDto.class);
            generalEntityDao.mergeEntity(countryDto);
        }
        else {
            ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
            generalEntityDao.mergeEntity(constantPayloadDto);
        }
    }

    public void deleteConstantEntity(final String serviceId,
                                     final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            generalEntityDao.removeEntity(value);
        }
        else {
            generalEntityDao.removeEntity(value);
        }
    }

    public List<?> getConstantEntities(final String location,
                                       final String serviceId) {
        return Collections.emptyList();
    }

    public Object getConstantEntity(final String location,
                                    final String serviceId,
                                    final String value) {
        Pair<String, String> pair = Pair.of(location, value);
        return generalEntityDao.findEntity("", List.of(pair));
    }
}
