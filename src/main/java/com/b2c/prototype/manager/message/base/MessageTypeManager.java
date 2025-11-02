package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.manager.message.IMessageTypeManager;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class MessageTypeManager implements IMessageTypeManager {

    private final IGeneralEntityDao generalEntityDao;

    public MessageTypeManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(MessageType payload) {
        generalEntityDao.persistEntity(payload);
    }

    @Transactional
    public void mergeEntity(String searchValue, MessageType payload) {
        MessageType fetchedEntity =
                generalEntityDao.findEntity("MessageType.findByKey", Pair.of(KEY, searchValue));
        payload.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(payload);
    }

    public void removeEntity(String value) {
        MessageType fetchedEntity = generalEntityDao.findEntity("MessageType.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public MessageType getEntity(String value) {
        return generalEntityDao.findEntity("MessageType.findByKey", Pair.of(KEY, value));
    }

    public Optional<MessageType> getEntityOptional(String value) {
        MessageType entity = generalEntityDao.findEntity("MessageType.findByKey", Pair.of(KEY, value));
        return Optional.of(entity);
    }

    public List<MessageType> getEntities() {
        return generalEntityDao.findEntityList("MessageType.all", (Pair<String, ?>) null);
    }
}
