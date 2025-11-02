package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.manager.userdetails.ICountryPhoneCodeManager;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class CountryPhoneCodeManager implements ICountryPhoneCodeManager {
    
    private final IGeneralEntityDao generalEntityDao;

    public CountryPhoneCodeManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    @Override
    public void persistEntity(CountryPhoneCode entity) {
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, CountryPhoneCode entity) {
        CountryPhoneCode fetchedEntity =
                generalEntityDao.findEntity("CountryPhoneCode.findByKey", Pair.of(KEY, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        CountryPhoneCode fetchedEntity = generalEntityDao.findEntity("CountryPhoneCode.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    @Override
    public CountryPhoneCode getEntity(String value) {
        return generalEntityDao.findEntity("CountryPhoneCode.findByKey", Pair.of(KEY, value));
    }

    @Override
    public Optional<CountryPhoneCode> getEntityOptional(String value) {
        CountryPhoneCode entity = generalEntityDao.findEntity("CountryPhoneCode.findByKey", Pair.of(KEY, value));
        return Optional.of(entity);
    }

    @Override
    public List<CountryPhoneCode> getEntities() {
        return generalEntityDao.findEntityList("CountryPhoneCode.all", (Pair<String, ?>) null);
    }
}
