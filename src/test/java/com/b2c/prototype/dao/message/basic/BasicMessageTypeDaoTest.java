package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.dao.message.BasicMessageTypeDao;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicMessageTypeDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(MessageType.class, "message_type"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicMessageTypeDao(sessionFactory, queryService);
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
                .label("InMail")
                .build();
        return new EntityDataSet<>(messageType, "/datasets/message/message_type/testMessageTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        MessageType messageType = MessageType.builder()
                .value("InMail")
                .label("InMail")
                .build();
        return new EntityDataSet<>(messageType, "/datasets/message/message_type/saveMessageTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("Update InMail")
                .label("InMail")
                .build();
        return new EntityDataSet<>(messageType, "/datasets/message/message_type/updateMessageTypeDataSet.yml");
    }
}