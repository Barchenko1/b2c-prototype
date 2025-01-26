package com.b2c.prototype.dao.message.basic;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

class BasicMessageDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Message.class, "message"));
        entityIdentifierDao = new EntityIdentifierDao(entityMappingManager);
        dao = new BasicMessageDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM message_receivers");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/message/message/emptyMessageDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("New")
                .build();
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("InMail")
                .build();
        Message message = Message.builder()
                .id(1L)
                .title("title")
                .message("message")
                .messageUniqNumber("messageUniqNumber1")
                .sender("sender@email.com")
                .receivers(List.of("receiver1@email.com", "receiver2@email.com"))
                .subscribe("system")
                .sendSystem("system")
                .dateOfSend(100)
                .status(messageStatus)
                .type(messageType)
                .build();
        return new EntityDataSet<>(message, "/datasets/message/message/testMessageDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("New")
                .build();
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("InMail")
                .build();
        Message message = Message.builder()
                .title("title")
                .message("message")
                .sender("sender@email.com")
                .messageUniqNumber("messageUniqNumber1")
                .receivers(List.of("receiver1@email.com", "receiver2@email.com"))
                .subscribe("system")
                .sendSystem("system")
                .dateOfSend(100)
                .status(messageStatus)
                .type(messageType)
                .build();
        return new EntityDataSet<>(message, "/datasets/message/message/saveMessageDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("New")
                .build();
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("InMail")
                .build();
        Message message = Message.builder()
                .id(1L)
                .title("title")
                .message("message")
                .sender("sender@email.com")
                .messageUniqNumber("messageUniqNumber1")
                .receivers(List.of("receiver1@email.com", "receiver2@email.com"))
                .subscribe("system")
                .sendSystem("system")
                .dateOfSend(100)
                .status(messageStatus)
                .type(messageType)
                .build();
        return new EntityDataSet<>(message, "/datasets/message/message/updateMessageDataSet.yml");
    }
}