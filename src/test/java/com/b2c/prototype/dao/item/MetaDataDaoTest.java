package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetaDataDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/item/metadata/emptyMetaDataSet.yml", cleanBefore = true,
    executeStatementsBefore = {
            "TRUNCATE TABLE brand RESTART IDENTITY CASCADE",
            "TRUNCATE TABLE item_type RESTART IDENTITY CASCADE",
            "TRUNCATE TABLE category RESTART IDENTITY CASCADE",
    })
    @ExpectedDataSet(value = "datasets/dao/item/metadata/saveMetaDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        MetaData entity = getMetaData();
        entity.setId(0);
//        entity.getBrand().setId(0);
//        entity.getItemType().setId(0);
        Category category = Category.builder()
                .id(0L)
                .value("parent")
                .key("parent")
                .build();
//       entity.setCategory(category);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/metadata/testMetaDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/metadata/updateMetaDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        MetaData entity = getMetaData();
        entity.setDescription(new HashMap<>() {{
            put("desc1", "desc1");
            put("desc2", "desc2");
            put("desc3", "desc3");
        }});

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/metadata/testMetaDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/metadata/removeMetaDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        MetaData entity = getMetaData();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/metadata/testMetaDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        MetaData expected = getMetaData();

        Pair<String, Long> pair = Pair.of("id", 1L);
        MetaData entity = generalEntityDao.findEntity("MetaData.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/metadata/testMetaDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        MetaData expected = getMetaData();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<MetaData> optionEntity = generalEntityDao.findOptionEntity("MetaData.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        MetaData entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/metadata/testMetaDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        MetaData entity = getMetaData();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<MetaData> entityList = generalEntityDao.findEntityList("MetaData.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Category getCategory() {
        Category parent = Category.builder()
                .id(1L)
                .key("parent")
                .value("parent")
                .build();
        Category root = Category.builder()
                .id(2L)
                .key("root")
                .value("root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .id(3L)
                .key("child")
                .value("child")
                .build();

        parent.setChildList(List.of(root));
        root.setParent(parent);
        root.setChildList(List.of(child));
        child.setParent(root);

        return child;
    }

    private MetaData getMetaData() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .key("Hermes")
                .build();
        Category category = getCategory();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .key("Clothes")
                .build();

        Map<String, String> description = new HashMap<>(){{
            put("desc1", "desc1");
            put("desc2", "desc2");
        }};

        return MetaData.builder()
                .id(1L)
                .metadataUniqId("123")
                .description(description)
//                .category(category)
//                .brand(brand)
//                .itemType(itemType)
                .build();
    }

}
