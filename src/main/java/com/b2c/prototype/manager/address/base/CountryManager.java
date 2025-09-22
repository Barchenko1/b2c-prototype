package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.manager.address.ICountryManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

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
        Country fetchedEntity =
                generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    public void removeEntity(String value) {
        Country fetchedEntity = generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public Country getEntity(String value) {
        return generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, value));
    }

    public Optional<Country> getEntityOptional(String value) {
        Country entity = generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, value));
        return Optional.of(entity);
    }


    public List<Country> getEntities() {
        return generalEntityDao.findEntityList("Country.all", (Pair<String, ?>) null);
    }
}
