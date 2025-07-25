package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.dao.message.BasicMessageStatusDao;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicMessageStatusDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(MessageStatus.class, "message_status"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicMessageStatusDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/message/message_status/emptyMessageStatusDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("New")
                .label("New")
                .build();
        return new EntityDataSet<>(messageStatus, "/datasets/message/message_status/testMessageStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        MessageStatus messageStatus = MessageStatus.builder()
                .value("New")
                .label("New")
                .build();
        return new EntityDataSet<>(messageStatus, "/datasets/message/message_status/saveMessageStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("Update New")
                .label("New")
                .build();
        return new EntityDataSet<>(messageStatus, "/datasets/message/message_status/updateMessageStatusDataSet.yml");
    }
}