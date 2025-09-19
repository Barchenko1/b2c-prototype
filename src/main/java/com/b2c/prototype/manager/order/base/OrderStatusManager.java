package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class OrderStatusManager implements IOrderStatusManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;

    public OrderStatusManager(IGeneralEntityDao orderStatusDao,
                              IOrderTransformService orderTransformService) {
        this.generalEntityDao = orderStatusDao;
        this.orderTransformService = orderTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        OrderStatus entity = orderTransformService.mapConstantPayloadDtoToOrderStatus(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        OrderStatus fetchedEntity =
                generalEntityDao.findEntity("OrderStatus.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        OrderStatus fetchedEntity = generalEntityDao.findEntity("OrderStatus.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        OrderStatus entity = generalEntityDao.findEntity("OrderStatus.findByValue", Pair.of(VALUE, value));
        return orderTransformService.mapOrderStatusToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        OrderStatus entity = generalEntityDao.findEntity("OrderStatus.findByValue", Pair.of(VALUE, value));
        return Optional.of(orderTransformService.mapOrderStatusToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("OrderStatus.all", (Pair<String, ?>) null).stream()
                .map(e -> orderTransformService.mapOrderStatusToConstantPayloadDto((OrderStatus) e))
                .toList();
    }
}
