package com.b2c.prototype.dao.address.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.address.Country;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.junit.jupiter.api.BeforeAll;

public class BasicCountryDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Country.class, "country"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicCountryDao(sessionFactory, entityIdentifierDao);
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
