package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.manager.item.IItemTypeManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class ItemTypeManager implements IItemTypeManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public ItemTypeManager(IGeneralEntityDao generalEntityDao,
                           IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        ItemType entity = itemTransformService.mapConstantPayloadDtoToItemType(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        ItemType fetchedEntity =
                generalEntityDao.findEntity("ItemType.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        ItemType fetchedEntity = generalEntityDao.findEntity("ItemType.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        ItemType entity = generalEntityDao.findEntity("ItemType.findByValue", Pair.of(VALUE, value));
        return itemTransformService.mapItemTypeToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        ItemType entity = generalEntityDao.findEntity("ItemType.findByValue", Pair.of(VALUE, value));
        return Optional.of(itemTransformService.mapItemTypeToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("ItemType.all", (Pair<String, ?>) null).stream()
                .map(e -> itemTransformService.mapItemTypeToConstantPayloadDto((ItemType) e))
                .toList();
    }
}
