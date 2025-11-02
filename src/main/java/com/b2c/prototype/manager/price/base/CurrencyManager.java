package com.b2c.prototype.manager.price.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.manager.price.ICurrencyManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class CurrencyManager implements ICurrencyManager {
    
    private final IGeneralEntityDao generalEntityDao;

    public CurrencyManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(Currency entity) {
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, Currency entity) {
        Currency fetchedEntity =
                generalEntityDao.findEntity("Currency.findByKey", Pair.of(KEY, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        Currency fetchedEntity = generalEntityDao.findEntity("Currency.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public Currency getEntity(String value) {
        return generalEntityDao.findEntity("Currency.findByKey", Pair.of(KEY, value));
    }

    public Optional<Currency> getEntityOptional(String value) {
        Currency entity = generalEntityDao.findEntity("Currency.findByKey", Pair.of(KEY, value));
        return Optional.of(entity);
    }


    public List<Currency> getEntities() {
        return generalEntityDao.findEntityList("Currency.all", (Pair<String, ?>) null);
    }
}
