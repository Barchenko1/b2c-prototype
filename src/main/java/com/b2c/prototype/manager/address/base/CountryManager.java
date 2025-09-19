package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.transform.address.IAddressTransformService;
import com.b2c.prototype.manager.address.ICountryManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class CountryManager implements ICountryManager {
    
    private final IGeneralEntityDao generalEntityDao;
    private final IAddressTransformService addressTransformService;

    public CountryManager(IGeneralEntityDao generalEntityDao, IAddressTransformService addressTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.addressTransformService = addressTransformService;
    }

    public void saveEntity(CountryDto payload) {
        Country entity = addressTransformService.mapConstantPayloadDtoToCountry(payload);
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
        return addressTransformService.mapCountryToConstantPayloadDto(entity);
    }

    public Optional<CountryDto> getEntityOptional(String value) {
        Country entity = generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, value));
        return Optional.of(addressTransformService.mapCountryToConstantPayloadDto(entity));
    }


    public List<CountryDto> getEntities() {
        return generalEntityDao.findEntityList("Country.all", (Pair<String, ?>) null).stream()
                .map(e -> addressTransformService.mapCountryToConstantPayloadDto((Country) e))
                .toList();
    }
}
