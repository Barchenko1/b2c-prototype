package com.b2c.prototype.manager.rating.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.rating.IRatingManager;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class RatingManager implements IRatingManager {
    
    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public RatingManager(IGeneralEntityDao generalEntityDao,
                         IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    public void saveEntity(NumberConstantPayloadDto payload) {
        Rating entity = itemTransformService.mapNumberConstantPayloadDtoToRating(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(Integer searchValue, NumberConstantPayloadDto payload) {
        Rating fetchedEntity =
                generalEntityDao.findEntity("Rating.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue().intValue());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(int value) {
        Rating fetchedEntity = generalEntityDao.findEntity("Rating.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public NumberConstantPayloadDto getEntity(int value) {
        Rating entity = generalEntityDao.findEntity("Rating.findByValue", Pair.of(VALUE, value));
        return itemTransformService.mapRatingToNumberConstantPayloadDto(entity);
    }

    public Optional<NumberConstantPayloadDto> getEntityOptional(int value) {
        Rating entity = generalEntityDao.findEntity("Rating.findByValue", Pair.of(VALUE, value));
        return Optional.of(itemTransformService.mapRatingToNumberConstantPayloadDto(entity));
    }


    public List<NumberConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("Rating.all", (Pair<String, ?>) null).stream()
                .map(e -> itemTransformService.mapRatingToNumberConstantPayloadDto((Rating) e))
                .toList();
    }
}
