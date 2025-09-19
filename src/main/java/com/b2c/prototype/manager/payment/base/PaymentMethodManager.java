package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.manager.payment.IPaymentMethodManager;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class PaymentMethodManager implements IPaymentMethodManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;

    public PaymentMethodManager(IGeneralEntityDao generalEntityDao, IOrderTransformService orderTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.orderTransformService = orderTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        PaymentMethod entity = orderTransformService.mapConstantPayloadDtoToPaymentMethod(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        PaymentMethod fetchedEntity =
                generalEntityDao.findEntity("PaymentMethod.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        PaymentMethod fetchedEntity = generalEntityDao.findEntity("PaymentMethod.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        PaymentMethod entity = generalEntityDao.findEntity("PaymentMethod.findByValue", Pair.of(VALUE, value));
        return orderTransformService.mapPaymentMethodToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        PaymentMethod entity = generalEntityDao.findEntity("PaymentMethod.findByValue", Pair.of(VALUE, value));
        return Optional.of(orderTransformService.mapPaymentMethodToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("PaymentMethod.all", (Pair<String, ?>) null).stream()
                .map(e -> orderTransformService.mapPaymentMethodToConstantPayloadDto((PaymentMethod) e))
                .toList();
    }
}
