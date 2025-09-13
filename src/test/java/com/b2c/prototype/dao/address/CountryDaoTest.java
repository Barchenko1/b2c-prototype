package com.b2c.prototype.dao.address;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Country;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CountryDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/constant/country/emptyCountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/constant/country/saveCountryDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Country entity = getCountry();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/constant/country/testCountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/constant/country/updateCountryDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Country entity = getCountry();
        entity.setValue("Update USA");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/constant/country/testCountryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/constant/country/emptyCountryDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Country entity = getCountry();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/constant/country/testCountryDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Country expected = getCountry();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Country entity = generalEntityDao.findEntity("Country.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/constant/country/testCountryDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Country expected = getCountry();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Country> optionEntity = generalEntityDao.findOptionEntity("Country.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Country entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/constant/country/testCountryDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Country entity = getCountry();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Country> entityList = generalEntityDao.findEntityList("Country.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Country getCountry() {
        return Country.builder()
                .id(1L)
                .value("USA")
                .label("USA")
                .build();
    }

}
