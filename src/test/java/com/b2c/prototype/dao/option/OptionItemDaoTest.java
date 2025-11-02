package com.b2c.prototype.dao.option;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionItemDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/option/option_item/emptyOptionItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/option/option_item/saveOptionItemDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        OptionItem entity = getOptionItem();
        entity.setId(0);
        entity.getOptionGroup().setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_item/testOptionItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/option/option_item/updateOptionItemDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        OptionItem entity = getOptionItem();
        entity.setValue("XL");
        entity.setKey("XL");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_item/testOptionItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/option/option_item/removeOptionItemDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        OptionItem entity = getOptionItem();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_item/testOptionItemDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        OptionItem expected = getOptionItem();

        Pair<String, Long> pair = Pair.of("id", 1L);
        OptionItem entity = generalEntityDao.findEntity("OptionItem.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_item/testOptionItemDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        OptionItem expected = getOptionItem();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<OptionItem> optionEntity = generalEntityDao.findOptionEntity("OptionItem.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        OptionItem entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/option/option_item/testOptionItemDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        OptionItem entity = getOptionItem();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<OptionItem> entityList = generalEntityDao.findEntityList("OptionItem.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    public OptionItem getOptionItem() {
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .value("L")
                .key("L")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .key("Size")
                .build();

        optionGroup.addOptionItem(optionItem);
        return optionItem;
    }

}
