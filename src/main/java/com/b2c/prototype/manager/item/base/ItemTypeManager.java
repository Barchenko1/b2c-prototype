package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.manager.item.IItemTypeManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class ItemTypeManager implements IItemTypeManager {

    private final IGeneralEntityDao generalEntityDao;

    public ItemTypeManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(ItemType payload) {
        generalEntityDao.persistEntity(payload);
    }

    public void mergeEntity(String searchValue, ItemType entity) {
        ItemType fetchedEntity =
                generalEntityDao.findEntity("ItemType.findByValue", Pair.of(VALUE, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    public void removeEntity(String value) {
        ItemType fetchedEntity = generalEntityDao.findEntity("ItemType.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ItemType getEntity(String value) {
        return generalEntityDao.findEntity("ItemType.findByValue", Pair.of(VALUE, value));
    }

    public Optional<ItemType> getEntityOptional(String value) {
        ItemType entity = generalEntityDao.findEntity("ItemType.findByValue", Pair.of(VALUE, value));
        return Optional.of(entity);
    }


    public List<ItemType> getEntities() {
        return generalEntityDao.findEntityList("ItemType.all", (Pair<String, ?>) null);
    }
}
