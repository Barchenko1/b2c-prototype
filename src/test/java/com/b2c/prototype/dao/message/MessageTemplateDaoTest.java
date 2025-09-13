package com.b2c.prototype.dao.message;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
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

class MessageTemplateDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/message/message_template/emptyMessageTemplateDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/message/message_template/saveMessageTemplateDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        MessageTemplate entity = getMessageTemplate();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/message/message_template/testMessageTemplateDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/message/message_template/updateMessageTemplateDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        MessageTemplate entity = getMessageTemplate();
//        entity.setValue("Update New");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/message/message_template/testMessageTemplateDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/message/message_template/emptyMessageTemplateDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        MessageTemplate entity = getMessageTemplate();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/message/message_template/testMessageTemplateDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        MessageTemplate expected = getMessageTemplate();

        Pair<String, Long> pair = Pair.of("id", 1L);
        MessageTemplate entity = generalEntityDao.findEntity("MessageTemplate.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/message/message_template/testMessageTemplateDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        MessageTemplate expected = getMessageTemplate();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<MessageTemplate> optionEntity = generalEntityDao.findOptionEntity("MessageTemplate.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        MessageTemplate entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/message/message_template/testMessageTemplateDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        MessageTemplate entity = getMessageTemplate();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<MessageTemplate> entityList = generalEntityDao.findEntityList("MessageTemplate.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private MessageTemplate getMessageTemplate() {
        return MessageTemplate.builder()
                .id(1L)
                .title("title")
                .message("message")
                .build();
    }

}