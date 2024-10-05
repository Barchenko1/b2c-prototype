package com.b2c.prototype.dao.address.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.address.Country;
import org.junit.jupiter.api.BeforeAll;

public class BasicCountryDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
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
                .name("USA")
                .build();
        return new EntityDataSet<>(country, "/datasets/address/country/testCountryDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Country country = Country.builder()
                .name("USA")
                .build();
        return new EntityDataSet<>(country, "/datasets/address/country/saveCountryDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Country country = Country.builder()
                .id(1L)
                .name("Update USA")
                .build();
        return new EntityDataSet<>(country, "/datasets/address/country/updateCountryDataSet.yml");
    }

}
