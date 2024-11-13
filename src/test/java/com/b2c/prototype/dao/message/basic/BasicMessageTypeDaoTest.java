package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicMessageTypeDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(MessageType.class, "message_type"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicMessageTypeDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/message/message_type/emptyMessageTypeDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("InMail")
                .build();
        return new EntityDataSet<>(messageType, "/datasets/message/message_type/testMessageTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        MessageType messageType = MessageType.builder()
                .value("InMail")
                .build();
        return new EntityDataSet<>(messageType, "/datasets/message/message_type/saveMessageTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("Update InMail")
                .build();
        return new EntityDataSet<>(messageType, "/datasets/message/message_type/updateMessageTypeDataSet.yml");
    }
}