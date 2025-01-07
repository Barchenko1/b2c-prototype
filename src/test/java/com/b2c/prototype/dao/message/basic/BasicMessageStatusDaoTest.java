package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicMessageStatusDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(MessageStatus.class, "message_status"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicMessageStatusDao(sessionFactory, entityIdentifierDao);
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