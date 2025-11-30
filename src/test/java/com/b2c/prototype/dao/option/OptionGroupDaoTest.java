package com.b2c.prototype.dao.option;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionGroupDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/option/option_group/emptyOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/option/option_group/saveOptionGroupDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        OptionGroup entity = getOptionGroup();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_group/testOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/option/option_group/updateOptionGroupDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        OptionGroup entity = getOptionGroup();
        entity.setKey("Color");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_group/testOptionGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/option/option_group/emptyOptionGroupDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        OptionGroup entity = getOptionGroup();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_group/testOptionGroupDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        OptionGroup expected = getOptionGroup();

        Pair<String, Long> pair = Pair.of("id", 1L);
        OptionGroup entity = generalEntityDao.findEntity("OptionGroup.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_group/testOptionGroupDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        OptionGroup expected = getOptionGroup();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<OptionGroup> optionEntity = generalEntityDao.findOptionEntity("OptionGroup.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        OptionGroup entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_group/testOptionGroupDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        OptionGroup entity = getOptionGroup();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<OptionGroup> entityList = generalEntityDao.findEntityList("OptionGroup.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private OptionGroup getOptionGroup() {
        return OptionGroup.builder()
                .id(1L)
                .value("Color")
                .key("Color")
                .build();
    }

}
