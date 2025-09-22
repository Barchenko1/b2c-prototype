package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.manager.message.IMessageStatusManager;

import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class MessageStatusManager implements IMessageStatusManager {

    private final IGeneralEntityDao generalEntityDao;

    public MessageStatusManager(IGeneralEntityDao generalEntityDao) {

        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(MessageStatus entity) {
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, MessageStatus entity) {
        MessageStatus fetchedEntity =
                generalEntityDao.findEntity("MessageStatus.findByValue", Pair.of(VALUE, searchValue));
        entity.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        MessageStatus fetchedEntity = generalEntityDao.findEntity("MessageStatus.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public MessageStatus getEntity(String value) {
        return generalEntityDao.findEntity("MessageStatus.findByValue", Pair.of(VALUE, value));
    }

    public Optional<MessageStatus> getEntityOptional(String value) {
        MessageStatus entity = generalEntityDao.findEntity("MessageStatus.findByValue", Pair.of(VALUE, value));
        return Optional.of(entity);
    }

    public List<MessageStatus> getEntities() {
        return generalEntityDao.findEntityList("MessageStatus.all", (Pair<String, ?>) null);
    }

}
