package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicOptionGroupDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(OptionGroup.class, "option_group"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
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