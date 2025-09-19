package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.manager.message.IMessageTypeManager;
import com.b2c.prototype.transform.userdetails.IUserDetailsTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class MessageTypeManager implements IMessageTypeManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IUserDetailsTransformService userDetailsTransformService;

    public MessageTypeManager(IGeneralEntityDao generalEntityDao,
                              IUserDetailsTransformService userDetailsTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.userDetailsTransformService = userDetailsTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        MessageType entity = userDetailsTransformService.mapConstantPayloadDtoToMessageType(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        MessageType fetchedEntity =
                generalEntityDao.findEntity("MessageType.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        MessageType fetchedEntity = generalEntityDao.findEntity("MessageType.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        MessageType entity = generalEntityDao.findEntity("MessageType.findByValue", Pair.of(VALUE, value));
        return userDetailsTransformService.mapMessageTypeToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        MessageType entity = generalEntityDao.findEntity("MessageType.findByValue", Pair.of(VALUE, value));
        return Optional.of(userDetailsTransformService.mapMessageTypeToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("MessageType.all", (Pair<String, ?>) null).stream()
                .map(e -> userDetailsTransformService.mapMessageTypeToConstantPayloadDto((MessageType) e))
                .toList();
    }
}
