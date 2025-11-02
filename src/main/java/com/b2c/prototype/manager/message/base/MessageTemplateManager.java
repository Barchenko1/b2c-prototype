package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.message.IMessageTemplateManager;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.MESSAGE_TEMPLATE_UNIQ_ID;
import static com.b2c.prototype.util.Util.getUUID;

@Service
public class MessageTemplateManager implements IMessageTemplateManager {

    private final IGeneralEntityDao generalEntityDao;

    public MessageTemplateManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(MessageTemplate entity) {
        entity.setMessageTemplateUniqId(getUUID());
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, MessageTemplate entity) {
        MessageTemplate fetchedEntity =
                generalEntityDao.findEntity("MessageTemplate.findByMessageTemplateId", Pair.of(MESSAGE_TEMPLATE_UNIQ_ID, searchValue));
        entity.setMessageTemplateUniqId(getUUID());
        entity.setOriginal(fetchedEntity);
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        MessageTemplate fetchedEntity = generalEntityDao.findEntity("MessageTemplate.findByMessageTemplateId", Pair.of(MESSAGE_TEMPLATE_UNIQ_ID, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public MessageTemplate getEntity(String value) {
        return generalEntityDao.findEntity("MessageTemplate.findByMessageTemplateId", Pair.of(MESSAGE_TEMPLATE_UNIQ_ID, value));
    }

    public Optional<MessageTemplate> getEntityOptional(String value) {
        MessageTemplate entity = generalEntityDao.findEntity("MessageTemplate.findByMessageTemplateId", Pair.of(MESSAGE_TEMPLATE_UNIQ_ID, value));
        return Optional.of(entity);
    }

    public List<MessageTemplate> getEntities() {
        return generalEntityDao.findEntityList("MessageTemplate.all", (Pair<String, ?>) null);
    }
}
