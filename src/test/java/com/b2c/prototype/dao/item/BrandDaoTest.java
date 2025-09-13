package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.Brand;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BrandDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/item/brand/emptyBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/brand/saveBrandDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Brand entity = getBrand();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/brand/testBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/brand/updateBrandDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Brand entity = getBrand();
        entity.setValue("Update Apple");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/brand/testBrandDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/brand/emptyBrandDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Brand entity = getBrand();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/brand/testBrandDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Brand expected = getBrand();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Brand entity = generalEntityDao.findEntity("Brand.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/item/brand/testBrandDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Brand expected = getBrand();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Brand> optionEntity = generalEntityDao.findOptionEntity("Brand.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Brand entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/item/brand/testBrandDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Brand entity = getBrand();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Brand> entityList = generalEntityDao.findEntityList("Brand.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Brand getBrand() {
        return Brand.builder()
                .id(1L)
                .value("Apple")
                .label("Apple")
                .build();
    }

}