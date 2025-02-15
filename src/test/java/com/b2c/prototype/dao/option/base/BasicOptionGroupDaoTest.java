package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicOptionGroupDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(OptionGroup.class, "option_group"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicOptionGroupDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/option/option_group/emptyOptionGroupDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Color")
                .label("Color")
                .build();
        return new EntityDataSet<>(optionGroup, "/datasets/option/option_group/testOptionGroupDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        OptionGroup optionGroup = OptionGroup.builder()
                .value("Color")
                .label("Color")
                .build();
        return new EntityDataSet<>(optionGroup, "/datasets/option/option_group/saveOptionGroupDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Update Color")
                .label("Color")
                .build();
        return new EntityDataSet<>(optionGroup, "/datasets/option/option_group/updateOptionGroupDataSet.yml");
    }
}