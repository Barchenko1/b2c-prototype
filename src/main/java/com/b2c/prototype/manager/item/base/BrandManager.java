package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.manager.item.IBrandManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class BrandManager implements IBrandManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public BrandManager(IGeneralEntityDao brandDao,
                        IItemTransformService itemTransformService) {
        this.generalEntityDao = brandDao;
        this.itemTransformService = itemTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        Brand entity = itemTransformService.mapConstantPayloadDtoToBrand(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        Brand fetchedEntity =
                generalEntityDao.findEntity("Brand.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        Brand fetchedEntity = generalEntityDao.findEntity("Brand.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        Brand entity = generalEntityDao.findEntity("Brand.findByValue", Pair.of(VALUE, value));
        return itemTransformService.mapBrandToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        Brand entity = generalEntityDao.findEntity("Brand.findByValue", Pair.of(VALUE, value));
        return Optional.of(itemTransformService.mapBrandToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("Brand.all", (Pair<String, ?>) null).stream()
                .map(e -> itemTransformService.mapBrandToConstantPayloadDto((Brand) e))
                .toList();
    }

}
