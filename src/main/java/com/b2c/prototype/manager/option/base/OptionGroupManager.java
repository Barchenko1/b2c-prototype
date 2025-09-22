package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class OptionGroupManager implements IOptionGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public OptionGroupManager(IGeneralEntityDao generalEntityDao,
                              IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    public void persistEntity(ConstantPayloadDto payload) {
        OptionGroup entity = itemTransformService.mapConstantPayloadDtoToOptionGroup(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void mergeEntity(String searchValue, ConstantPayloadDto entity) {
        OptionGroup fetchedEntity =
                generalEntityDao.findEntity("OptionGroup.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(entity.getValue());
        fetchedEntity.setLabel(entity.getLabel());

        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void removeEntity(String value) {
        OptionGroup fetchedEntity = generalEntityDao.findEntity("OptionGroup.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        OptionGroup entity = generalEntityDao.findEntity("OptionGroup.findByValue", Pair.of(VALUE, value));
        return itemTransformService.mapOptionGroupToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        OptionGroup entity = generalEntityDao.findEntity("OptionGroup.findByValue", Pair.of(VALUE, value));
        return Optional.of(itemTransformService.mapOptionGroupToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("OptionGroup.all", (Pair<String, ?>) null).stream()
                .map(e -> itemTransformService.mapOptionGroupToConstantPayloadDto((OptionGroup) e))
                .toList();
    }
}
