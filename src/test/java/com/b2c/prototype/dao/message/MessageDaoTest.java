package com.b2c.prototype.dao.message;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/message/message/emptyMessageDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE message_template RESTART IDENTITY CASCADE",
            })
    @ExpectedDataSet(value = "datasets/dao/message/message/saveMessageDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Message entity = getMessage();
        entity.setId(0);
        entity.getMessageTemplate().setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message/testMessageDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message/updateMessageDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Message entity = getMessage();
        entity.getMessageTemplate().setMessage("Update message");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message/testMessageDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message/removeMessageDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Message entity = getMessage();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message/testMessageDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Message expected = getMessage();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Message entity = generalEntityDao.findEntity("Message.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message/testMessageDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Message expected = getMessage();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Message> optionEntity = generalEntityDao.findOptionEntity("Message.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Message entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message/testMessageDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Message entity = getMessage();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Message> entityList = generalEntityDao.findEntityList("Message.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Message getMessage() {
        MessageStatus messageStatus = MessageStatus.builder()
                .id(1L)
                .value("New")
                .key("New")
                .build();
        MessageType messageType = MessageType.builder()
                .id(1L)
                .value("InMail")
                .key("InMail")
                .build();

        MessageTemplate messageTemplate = MessageTemplate.builder()
                .id(1L)
                .title("title")
                .message("message")
                .build();

        return Message.builder()
                .id(1L)
                .messageTemplate(messageTemplate)
                .status(messageStatus)
                .type(messageType)
//                .messageUniqId("messageUniqNumber1")
//                .sender("sender@email.com")
//                .receivers(Set.of("receiver1@email.com", "receiver2@email.com"))
                .dateOfSend(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
    }

}
