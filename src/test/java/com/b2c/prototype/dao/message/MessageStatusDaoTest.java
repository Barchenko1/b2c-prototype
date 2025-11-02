package com.b2c.prototype.dao.message;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageStatusDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/message/message_status/emptyMessageStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message_status/saveMessageStatusDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        MessageStatus entity = getMessageStatus();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_status/testMessageStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message_status/updateMessageStatusDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        MessageStatus entity = getMessageStatus();
        entity.setKey("Update New");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_status/testMessageStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message_status/emptyMessageStatusDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        MessageStatus entity = getMessageStatus();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_status/testMessageStatusDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        MessageStatus expected = getMessageStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        MessageStatus entity = generalEntityDao.findEntity("MessageStatus.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_status/testMessageStatusDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        MessageStatus expected = getMessageStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<MessageStatus> optionEntity = generalEntityDao.findOptionEntity("MessageStatus.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        MessageStatus entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_status/testMessageStatusDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        MessageStatus entity = getMessageStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<MessageStatus> entityList = generalEntityDao.findEntityList("MessageStatus.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private MessageStatus getMessageStatus() {
        return MessageStatus.builder()
                .id(1L)
                .key("New")
                .value("New")
                .build();
    }
}
