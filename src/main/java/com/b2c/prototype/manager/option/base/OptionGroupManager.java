package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.b2c.prototype.transform.option.IOptionTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class OptionGroupManager implements IOptionGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOptionTransformService optionTransformService;

    public OptionGroupManager(IGeneralEntityDao generalEntityDao,
                              IOptionTransformService optionTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.optionTransformService = optionTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        OptionGroup entity = optionTransformService.mapConstantPayloadDtoToOptionGroup(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        OptionGroup fetchedEntity =
                generalEntityDao.findEntity("OptionGroup.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        OptionGroup fetchedEntity = generalEntityDao.findEntity("OptionGroup.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        OptionGroup entity = generalEntityDao.findEntity("OptionGroup.findByValue", Pair.of(VALUE, value));
        return optionTransformService.mapOptionGroupToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        OptionGroup entity = generalEntityDao.findEntity("OptionGroup.findByValue", Pair.of(VALUE, value));
        return Optional.of(optionTransformService.mapOptionGroupToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("OptionGroup.all", (Pair<String, ?>) null).stream()
                .map(e -> optionTransformService.mapOptionGroupToConstantPayloadDto((OptionGroup) e))
                .toList();
    }
}
