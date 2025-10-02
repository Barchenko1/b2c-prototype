package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class OptionGroupManager implements IOptionGroupManager {

    private final IGeneralEntityDao generalEntityDao;

    public OptionGroupManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(OptionGroup entity) {
        entity.getOptionItems().forEach(entity::addOptionItem);
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, OptionGroup entity) {
        OptionGroup fetchedEntity =
                generalEntityDao.findEntity("OptionGroup.findByValueWithOptionItems", Pair.of(VALUE, searchValue));
        entity.getOptionItems().forEach(optionItem -> {

            entity.addOptionItem(optionItem);
        });
        Map<String, OptionItem> existingByValue = fetchedEntity.getOptionItems().stream()
                .collect(Collectors.toMap(OptionItem::getValue, Function.identity()));
        fetchedEntity.setLabel(entity.getLabel());
        fetchedEntity.setValue(entity.getValue());
        fetchedEntity.getOptionItems().forEach(optionItem -> {
            optionItem.setLabel(entity.getLabel());
            optionItem.setValue(entity.getLabel());
        });
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        OptionGroup fetchedEntity = generalEntityDao.findEntity("OptionGroup.findAllWithPriceAndCurrency", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public OptionGroup getEntity(String value) {
        return generalEntityDao.findEntity("OptionGroup.findAllWithPriceAndCurrency", Pair.of(VALUE, value));
    }

    public Optional<OptionGroup> getEntityOptional(String value) {
        OptionGroup entity = generalEntityDao.findEntity("OptionGroup.findAllWithPriceAndCurrency", Pair.of(VALUE, value));
        return Optional.of(entity);
    }


    public List<OptionGroup> getEntities() {
        return generalEntityDao.findEntityList("OptionGroup.findWithOptionItems", (Pair<String, ?>) null);
    }
}
