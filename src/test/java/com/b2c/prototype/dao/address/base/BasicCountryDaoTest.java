package com.b2c.prototype.dao.address.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.address.Country;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

public class BasicCountryDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Country.class, "country"));
queryService = new QueryService(entityMappingManager);
        dao = new BasicCountryDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/address/country/emptyCountryDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Country country = Country.builder()
                .id(1L)
                .label("USA")
                .value("USA")
                .build();
        return new EntityDataSet<>(country, "/datasets/address/country/testCountryDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Country country = Country.builder()
                .value("USA")
                .label("USA")
                .build();
        return new EntityDataSet<>(country, "/datasets/address/country/saveCountryDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Country country = Country.builder()
                .id(1L)
                .value("Update USA")
                .label("USA")
                .build();
        return new EntityDataSet<>(country, "/datasets/address/country/updateCountryDataSet.yml");
    }

}
