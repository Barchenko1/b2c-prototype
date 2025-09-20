package com.b2c.prototype.dao.message;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageTypeDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/message/message_type/emptyMessageTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message_type/saveMessageTypeDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        MessageType entity = getMessageType();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_type/testMessageTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message_type/updateMessageTypeDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        MessageType entity = getMessageType();
        entity.setValue("Update InMail");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_type/testMessageTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/message/message_type/emptyMessageTypeDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        MessageType entity = getMessageType();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_type/testMessageTypeDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        MessageType expected = getMessageType();

        Pair<String, Long> pair = Pair.of("id", 1L);
        MessageType entity = generalEntityDao.findEntity("MessageType.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_type/testMessageTypeDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        MessageType expected = getMessageType();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<MessageType> optionEntity = generalEntityDao.findOptionEntity("MessageType.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        MessageType entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/message/message_type/testMessageTypeDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        MessageType entity = getMessageType();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<MessageType> entityList = generalEntityDao.findEntityList("MessageType.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private MessageType getMessageType() {
        return MessageType.builder()
                .id(1L)
                .value("InMail")
                .label("InMail")
                .build();
    }
}
