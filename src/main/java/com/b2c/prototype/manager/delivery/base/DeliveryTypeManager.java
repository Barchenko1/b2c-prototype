package com.b2c.prototype.manager.delivery.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class DeliveryTypeManager implements IDeliveryTypeManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;

    public DeliveryTypeManager(IGeneralEntityDao generalEntityDao, IOrderTransformService orderTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.orderTransformService = orderTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        DeliveryType entity = orderTransformService.mapConstantPayloadDtoToDeliveryType(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        DeliveryType fetchedEntity =
                generalEntityDao.findEntity("DeliveryType.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        DeliveryType fetchedEntity = generalEntityDao.findEntity("DeliveryType.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        DeliveryType entity = generalEntityDao.findEntity("DeliveryType.findByValue", Pair.of(VALUE, value));
        return orderTransformService.mapDeliveryTypeToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        DeliveryType entity = generalEntityDao.findEntity("DeliveryType.findByValue", Pair.of(VALUE, value));
        return Optional.of(orderTransformService.mapDeliveryTypeToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("DeliveryType.all", (Pair<String, ?>) null).stream()
                .map(e -> orderTransformService.mapDeliveryTypeToConstantPayloadDto((DeliveryType) e))
                .toList();
    }
}
