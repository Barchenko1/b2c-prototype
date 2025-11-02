package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemTypeDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/item/item_type/emptyItemTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/item_type/saveItemTypeDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ItemType entity = getItemType();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/item_type/testItemTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/item_type/updateItemTypeDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ItemType entity = getItemType();
        entity.setKey("Update Clothes");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/item_type/testItemTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/item_type/emptyItemTypeDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ItemType entity = getItemType();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/item_type/testItemTypeDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ItemType expected = getItemType();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ItemType entity = generalEntityDao.findEntity("ItemType.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/item_type/testItemTypeDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ItemType expected = getItemType();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ItemType> optionEntity = generalEntityDao.findOptionEntity("ItemType.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ItemType entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/item_type/testItemTypeDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ItemType entity = getItemType();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ItemType> entityList = generalEntityDao.findEntityList("ItemType.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private ItemType getItemType() {
        return ItemType.builder()
                .id(1L)
                .value("Clothes")
                .key("Clothes")
                .build();
    }

}