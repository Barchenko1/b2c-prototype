package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.store.IAvailabilityStatusManager;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class AvailabilityStatusManager implements IAvailabilityStatusManager {

    private final IGeneralEntityDao generalEntityDao;

    public AvailabilityStatusManager(IGeneralEntityDao AvailabilityStatusDao) {
        this.generalEntityDao = AvailabilityStatusDao;
    }

    @Override
    public void persistEntity(AvailabilityStatus payload) {
        generalEntityDao.persistEntity(payload);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, AvailabilityStatus entity) {
        AvailabilityStatus fetchedEntity =
                generalEntityDao.findEntity("AvailabilityStatus.findByKey", Pair.of(KEY, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        AvailabilityStatus fetchedEntity = generalEntityDao.findEntity("AvailabilityStatus.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public AvailabilityStatus getEntity(String value) {
        return generalEntityDao.findEntity("AvailabilityStatus.findByKey", Pair.of(KEY, value));
    }

    public Optional<AvailabilityStatus> getEntityOptional(String value) {
        AvailabilityStatus entity = generalEntityDao.findEntity("AvailabilityStatus.findByKey", Pair.of(KEY, value));
        return Optional.of(entity);
    }

    public List<AvailabilityStatus> getEntities() {
        return generalEntityDao.findEntityList("AvailabilityStatus.all", (Pair<String, ?>) null);
    }

}
