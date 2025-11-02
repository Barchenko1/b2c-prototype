package com.b2c.prototype.manager.rating.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.rating.IRatingManager;
import com.b2c.prototype.modal.entity.review.Rating;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class RatingManager implements IRatingManager {
    
    private final IGeneralEntityDao generalEntityDao;

    public RatingManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(Rating entity) {
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(Integer searchValue, Rating entity) {
        Rating fetchedEntity =
                generalEntityDao.findEntity("Rating.findByKey", Pair.of(KEY, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(int value) {
        Rating fetchedEntity = generalEntityDao.findEntity("Rating.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public Rating getEntity(int value) {
        return generalEntityDao.findEntity("Rating.findByKey", Pair.of(KEY, value));
    }

    public Optional<Rating> getEntityOptional(int value) {
        Rating entity = generalEntityDao.findEntity("Rating.findByKey", Pair.of(KEY, value));
        return Optional.of(entity);
    }


    public List<Rating> getEntities() {
        return generalEntityDao.findEntityList("Rating.all", (Pair<String, ?>) null);
    }
}
