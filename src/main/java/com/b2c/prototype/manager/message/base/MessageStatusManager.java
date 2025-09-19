package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.manager.message.IMessageStatusManager;

import com.b2c.prototype.transform.message.IMessageTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class MessageStatusManager implements IMessageStatusManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IMessageTransformService messageTransformService;

    public MessageStatusManager(IGeneralEntityDao generalEntityDao,
                                IMessageTransformService messageTransformService) {

        this.generalEntityDao = generalEntityDao;
        this.messageTransformService = messageTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        MessageStatus entity = messageTransformService.mapConstantPayloadDtoToMessageStatus(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        ItemType fetchedEntity =
                generalEntityDao.findEntity("MessageStatus.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        MessageStatus fetchedEntity = generalEntityDao.findEntity("MessageStatus.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        MessageStatus entity = generalEntityDao.findEntity("MessageStatus.findByValue", Pair.of(VALUE, value));
        return messageTransformService.mapMessageStatusToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        MessageStatus entity = generalEntityDao.findEntity("MessageStatus.findByValue", Pair.of(VALUE, value));
        return Optional.of(messageTransformService.mapMessageStatusToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("MessageStatus.all", (Pair<String, ?>) null).stream()
                .map(e -> messageTransformService.mapMessageStatusToConstantPayloadDto((MessageStatus) e))
                .toList();
    }

}
