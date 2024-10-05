package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Brand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest // Loads the full Spring application context
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasicBrandDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        dao = new BasicBrandDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/brand/emptyBrandDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Brand brand = Brand.builder()
                .id(1L)
                .name("Apple")
                .build();
        return new EntityDataSet<>(brand, "/datasets/item/brand/testBrandDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Brand brand = Brand.builder()
                .name("Apple")
                .build();
        return new EntityDataSet<>(brand, "/datasets/item/brand/saveBrandDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Brand brand = Brand.builder()
                .id(1L)
                .name("Update Apple")
                .build();
        return new EntityDataSet<>(brand, "/datasets/item/brand/updateBrandDataSet.yml");
    }

}