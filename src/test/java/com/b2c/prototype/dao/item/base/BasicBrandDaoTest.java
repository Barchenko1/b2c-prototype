package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Brand;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicBrandDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Brand.class, "brand"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicBrandDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/brand/emptyBrandDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Apple")
                .label("Apple")
                .build();
        return new EntityDataSet<>(brand, "/datasets/item/brand/testBrandDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Brand brand = Brand.builder()
                .value("Apple")
                .label("Apple")
                .build();
        return new EntityDataSet<>(brand, "/datasets/item/brand/saveBrandDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Update Apple")
                .label("Apple")
                .build();
        return new EntityDataSet<>(brand, "/datasets/item/brand/updateBrandDataSet.yml");
    }

}