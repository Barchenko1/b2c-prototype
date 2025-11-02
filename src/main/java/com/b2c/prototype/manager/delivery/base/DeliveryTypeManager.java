package com.b2c.prototype.manager.delivery.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class DeliveryTypeManager implements IDeliveryTypeManager {

    private final IGeneralEntityDao generalEntityDao;

    public DeliveryTypeManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(DeliveryType entity) {
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    public void mergeEntity(String searchValue, DeliveryType entity) {
        DeliveryType fetchedEntity =
                generalEntityDao.findEntity("DeliveryType.findByKey", Pair.of(KEY, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    public void removeEntity(String value) {
        DeliveryType fetchedEntity = generalEntityDao.findEntity("DeliveryType.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public DeliveryType getEntity(String value) {
        return generalEntityDao.findEntity("DeliveryType.findByKey", Pair.of(KEY, value));
    }

    public Optional<DeliveryType> getEntityOptional(String value) {
        DeliveryType entity = generalEntityDao.findEntity("DeliveryType.findByKey", Pair.of(KEY, value));
        return Optional.of(entity);
    }


    public List<DeliveryType> getEntities() {
        return generalEntityDao.findEntityList("DeliveryType.all", (Pair<String, ?>) null);
    }
}
