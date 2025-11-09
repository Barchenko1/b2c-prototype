package com.b2c.prototype.dao.message;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.user.UserDetails;
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

class MessageBoxDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/message/message_box/emptyMessageBoxDataSet.yml", cleanBefore = true,
    executeStatementsBefore = {
            "TRUNCATE TABLE message_template RESTART IDENTITY CASCADE",
    })
    @ExpectedDataSet(value = "datasets/dao/message/message_box/saveMessageBoxDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        MessageBox entity = getMessageBox();
        entity.setId(0);
        entity.getMessages().forEach(message -> {
            message.setId(0);
            message.getMessageTemplate().setId(0);
        });

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_box/testMessageBoxDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message_box/updateMessageBoxDataSet.yml", orderBy = "id", ignoreCols = "id")
    public void mergeEntity_success() {
        MessageBox entity = getMessageBox();

        Message message = getMessage();
        message.setId(0);
        message.getMessageTemplate().setId(0);
//        message.setMessageUniqId("messageUniqNumber2");
//        entity.addMessage(message);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_box/testMessageBoxDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message_box/removeMessageBoxDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        MessageBox entity = getMessageBox();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_box/testMessageBoxDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        MessageBox expected = getMessageBox();

        Pair<String, Long> pair = Pair.of("id", 1L);
        MessageBox entity = generalEntityDao.findEntity("MessageBox.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_box/testMessageBoxDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        MessageBox expected = getMessageBox();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<MessageBox> optionEntity = generalEntityDao.findOptionEntity("MessageBox.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        MessageBox entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_box/testMessageBoxDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        MessageBox entity = getMessageBox();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<MessageBox> entityList = generalEntityDao.findEntityList("MessageBox.findById", List.of(pair));

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

    private MessageBox getMessageBox() {
        Message message = getMessage();

        UserDetails userDetails = UserDetails.builder()
                .id(1L)
                .userId("123")
                .username("username")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .isActive(false)
                .contactInfo(null)
                .build();

        MessageBox messageBox = MessageBox.builder()
                .id(1L)
                .userDetails(userDetails)
                .build();
//        messageBox.addMessage(message);

        return messageBox;
    }

}