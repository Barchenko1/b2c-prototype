package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.manager.item.IItemStatusManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class ArticularStatusManager implements IItemStatusManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;
    public ArticularStatusManager(IGeneralEntityDao generalEntityDao,
                                  IItemTransformService itemTransformService) {

        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        ArticularStatus entity = itemTransformService.mapConstantPayloadDtoToArticularStatus(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        ArticularStatus fetchedEntity =
                generalEntityDao.findEntity("ArticularStatus.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        ArticularStatus fetchedEntity = generalEntityDao.findEntity("ArticularStatus.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        ArticularStatus entity = generalEntityDao.findEntity("ArticularStatus.findByValue", Pair.of(VALUE, value));
        return itemTransformService.mapArticularStatusToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        ArticularStatus entity = generalEntityDao.findEntity("ArticularStatus.findByValue", Pair.of(VALUE, value));
        return Optional.of(itemTransformService.mapArticularStatusToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("ArticularStatus.all", (Pair<String, ?>) null).stream()
                .map(e -> itemTransformService.mapArticularStatusToConstantPayloadDto((ArticularStatus) e))
                .toList();
    }
}
