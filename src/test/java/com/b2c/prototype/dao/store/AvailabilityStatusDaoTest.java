package com.b2c.prototype.dao.store;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AvailabilityStatusDaoTest extends AbstractDaoTest {
    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/store/availability_status/emptyAvailabilityStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/store/availability_status/saveAvailabilityStatusDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        AvailabilityStatus entity = getAvailabilityStatus();
        entity.setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/availability_status/testAvailabilityStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/store/availability_status/updateAvailabilityStatusDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        AvailabilityStatus entity = getAvailabilityStatus();
        entity.setValue("Unavailable");
        entity.setKey("Unavailable");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/availability_status/testAvailabilityStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/store/availability_status/emptyAvailabilityStatusDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        AvailabilityStatus entity = getAvailabilityStatus();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/availability_status/testAvailabilityStatusDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        AvailabilityStatus expected = getAvailabilityStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        AvailabilityStatus entity = generalEntityDao.findEntity("AvailabilityStatus.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/availability_status/testAvailabilityStatusDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        AvailabilityStatus expected = getAvailabilityStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<AvailabilityStatus> optionEntity = generalEntityDao.findOptionEntity("AvailabilityStatus.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        AvailabilityStatus entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/availability_status/testAvailabilityStatusDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        AvailabilityStatus entity = getAvailabilityStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<AvailabilityStatus> entityList = generalEntityDao.findEntityList("AvailabilityStatus.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private AvailabilityStatus getAvailabilityStatus() {
        return AvailabilityStatus.builder()
                .id(1L)
                .value("Available")
                .key("Available")
                .build();
    }
}
