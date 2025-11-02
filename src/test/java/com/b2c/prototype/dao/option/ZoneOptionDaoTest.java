package com.b2c.prototype.dao.option;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZoneOptionDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/delivery/zone_option/emptyZoneOptionDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE zone_option RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE price RESTART IDENTITY CASCADE"
            }
    )
    @ExpectedDataSet(value = "datasets/dao/delivery/zone_option/saveZoneOptionDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ZoneOption entity = getZoneOption();
        entity.setId(0);
        entity.getPrice().setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/zone_option/testZoneOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/delivery/zone_option/updateZoneOptionDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ZoneOption entity = getZoneOption();
//        entity.setZoneName("Zone B");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/zone_option/testZoneOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/delivery/zone_option/emptyZoneOptionDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ZoneOption entity = getZoneOption();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/zone_option/testZoneOptionDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ZoneOption expected = getZoneOption();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ZoneOption entity = generalEntityDao.findEntity("ZoneOption.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/zone_option/testZoneOptionDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ZoneOption expected = getZoneOption();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ZoneOption> optionEntity = generalEntityDao.findOptionEntity("ZoneOption.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ZoneOption entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/zone_option/testZoneOptionDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ZoneOption entity = getZoneOption();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ZoneOption> entityList = generalEntityDao.findEntityList("ZoneOption.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private ZoneOption getZoneOption() {
        Country country = Country.builder()
                .id(1L)
                .value("Country")
                .key("Country")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .key("USD")
                .value("USD")
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(10)
                .currency(currency)
                .build();
        return ZoneOption.builder()
                .id(1L)
//                .zoneName("Zone A")
//                .country(country)
                .price(price)
//                .city("City")
                .build();
    }
}