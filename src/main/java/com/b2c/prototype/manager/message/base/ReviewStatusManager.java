package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.message.IReviewStatusManager;
import com.b2c.prototype.modal.entity.review.ReviewStatus;
import com.b2c.prototype.processor.item.IReviewStatusProcess;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class ReviewStatusManager implements IReviewStatusManager {
    private final IGeneralEntityDao generalEntityDao;

    public ReviewStatusManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(ReviewStatus payload) {
        generalEntityDao.persistEntity(payload);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, ReviewStatus payload) {
        ReviewStatus fetchedEntity =
                generalEntityDao.findEntity("ReviewStatus.findByValue", Pair.of(VALUE, searchValue));
        payload.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(payload);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        ReviewStatus fetchedEntity = generalEntityDao.findEntity("ReviewStatus.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ReviewStatus getEntity(String value) {
        return generalEntityDao.findEntity("ReviewStatus.findByValue", Pair.of(VALUE, value));
    }

    public Optional<ReviewStatus> getEntityOptional(String value) {
        ReviewStatus entity = generalEntityDao.findEntity("ReviewStatus.findByValue", Pair.of(VALUE, value));
        return Optional.of(entity);
    }

    public List<ReviewStatus> getEntities() {
        return generalEntityDao.findEntityList("ReviewStatus.all", (Pair<String, ?>) null);
    }
}
