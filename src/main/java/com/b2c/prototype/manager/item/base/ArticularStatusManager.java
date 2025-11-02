package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.manager.item.IArticularStatusManager;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class ArticularStatusManager implements IArticularStatusManager {

    private final IGeneralEntityDao generalEntityDao;

    public ArticularStatusManager(IGeneralEntityDao generalEntityDao) {

        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(ArticularStatus payload) {
        generalEntityDao.persistEntity(payload);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, ArticularStatus entity) {
        ArticularStatus fetchedEntity =
                generalEntityDao.findEntity("ArticularStatus.findByKey", Pair.of(KEY, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        ArticularStatus fetchedEntity = generalEntityDao.findEntity("ArticularStatus.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ArticularStatus getEntity(String value) {
        return generalEntityDao.findEntity("ArticularStatus.findByKey", Pair.of(KEY, value));
    }

    public Optional<ArticularStatus> getEntityOptional(String value) {
        ArticularStatus entity = generalEntityDao.findEntity("ArticularStatus.findByKey", Pair.of(KEY, value));
        return Optional.of(entity);
    }


    public List<ArticularStatus> getEntities() {
        return generalEntityDao.findEntityList("ArticularStatus.all", (Pair<String, ?>) null);
    }
}
