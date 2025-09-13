package com.b2c.prototype.dao.user;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.user.Device;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeviceDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/user/device/emptyDeviceDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/user/device/saveDeviceDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Device entity = getDevice();
        entity.setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/user/device/testDeviceDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/user/device/updateDeviceDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Device entity = getDevice();
        entity.setUserAgent("Update agent");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/user/device/testDeviceDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/user/device/emptyDeviceDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Device entity = getDevice();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/user/device/testDeviceDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Device expected = getDevice();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Device entity = generalEntityDao.findEntity("Device.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/user/device/testDeviceDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Device expected = getDevice();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Device> optionEntity = generalEntityDao.findOptionEntity("Device.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Device entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/user/device/testDeviceDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Device entity = getDevice();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Device> entityList = generalEntityDao.findEntityList("Device.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Device getDevice() {
        return Device.builder()
                .id(1L)
                .loginTime(getLocalDateTime("2024-03-03 12:00:00"))
                .userAgent("agent")
                .ipAddress("ipAddress")
                .language("language")
                .timezone("timezone")
                .platform("platform")
                .screenHeight(1080)
                .screenWidth(1920)
                .build();
    }

}