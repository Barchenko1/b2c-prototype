package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.manager.item.IBrandManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class BrandManager implements IBrandManager {

    private final IGeneralEntityDao generalEntityDao;

    public BrandManager(IGeneralEntityDao brandDao) {
        this.generalEntityDao = brandDao;
    }

    @Override
    public void persistEntity(Brand payload) {
        generalEntityDao.persistEntity(payload);
    }

    @Override
    public void mergeEntity(String searchValue, Brand entity) {
        Brand fetchedEntity =
                generalEntityDao.findEntity("Brand.findByValue", Pair.of(VALUE, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    public void removeEntity(String value) {
        Brand fetchedEntity = generalEntityDao.findEntity("Brand.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public Brand getEntity(String value) {
        return generalEntityDao.findEntity("Brand.findByValue", Pair.of(VALUE, value));
    }

    public Optional<Brand> getEntityOptional(String value) {
        Brand entity = generalEntityDao.findEntity("Brand.findByValue", Pair.of(VALUE, value));
        return Optional.of(entity);
    }

    public List<Brand> getEntities() {
        return generalEntityDao.findEntityList("Brand.all", (Pair<String, ?>) null);
    }

}
