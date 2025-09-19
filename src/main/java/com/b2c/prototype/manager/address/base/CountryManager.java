package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.manager.address.ICountryManager;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class CountryManager implements ICountryManager {
    
    private final IGeneralEntityDao generalEntityDao;
    private final IGeneralEntityTransformService generalEntityTransformService;

    public CountryManager(IGeneralEntityDao generalEntityDao,
                          IGeneralEntityTransformService generalEntityTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.generalEntityTransformService = generalEntityTransformService;
    }

    public void saveEntity(CountryDto payload) {
        Country entity = generalEntityTransformService.mapConstantPayloadDtoToCountry(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, CountryDto payload) {
        Country fetchedEntity =
                generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        Country fetchedEntity = generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public CountryDto getEntity(String value) {
        Country entity = generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, value));
        return generalEntityTransformService.mapCountryToConstantPayloadDto(entity);
    }

    public Optional<CountryDto> getEntityOptional(String value) {
        Country entity = generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, value));
        return Optional.of(generalEntityTransformService.mapCountryToConstantPayloadDto(entity));
    }


    public List<CountryDto> getEntities() {
        return generalEntityDao.findEntityList("Country.all", (Pair<String, ?>) null).stream()
                .map(e -> generalEntityTransformService.mapCountryToConstantPayloadDto((Country) e))
                .toList();
    }
}
