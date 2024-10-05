package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import org.junit.jupiter.api.BeforeAll;

class BasicOptionGroupDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        dao = new BasicOptionGroupDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/option/option_group/emptyOptionGroupDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .name("Color")
                .build();
        return new EntityDataSet<>(optionGroup, "/datasets/option/option_group/testOptionGroupDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        OptionGroup optionGroup = OptionGroup.builder()
                .name("Color")
                .build();
        return new EntityDataSet<>(optionGroup, "/datasets/option/option_group/saveOptionGroupDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .name("Update Color")
                .build();
        return new EntityDataSet<>(optionGroup, "/datasets/option/option_group/updateOptionGroupDataSet.yml");
    }
}