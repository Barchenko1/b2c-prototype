package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.manager.address.ICountryManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class CountryManager implements ICountryManager {
    
    private final IGeneralEntityDao generalEntityDao;

    public CountryManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(Country entity) {
        generalEntityDao.persistEntity(entity);
    }

    public void mergeEntity(String searchValue, Country entity) {
        Country fetchedEntity = getCountry(searchValue);
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    public void removeEntity(String key) {
        Country fetchedEntity = getCountry(key);
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public Country getEntity(String key) {
        return getCountry(key);
    }

    public Optional<Country> getEntityOptional(String key) {
        Country entity = getCountry(key);
        return Optional.of(entity);
    }


    public List<Country> getEntities() {
        return generalEntityDao.findEntityList("Country.all", (Pair<String, ?>) null);
    }

    private Country getCountry(String key) {
        return generalEntityDao.findEntity("Country.findByKey", Pair.of(KEY, key));
    }
}
