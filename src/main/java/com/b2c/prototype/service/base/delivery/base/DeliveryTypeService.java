package com.b2c.prototype.service.base.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.b2c.prototype.service.base.delivery.IDeliveryTypeService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;

public class DeliveryTypeService extends AbstractSingleEntityService implements IDeliveryTypeService {

    private final IDeliveryTypeDao deliveryTypeDao;
    private final IEntityStringMapWrapper<DeliveryType> deliveryTypeEntityMapWrapper;

    public DeliveryTypeService(IDeliveryTypeDao deliveryTypeDao,
                                           IEntityStringMapWrapper<DeliveryType> deliveryTypeEntityMapWrapper) {
        this.deliveryTypeDao = deliveryTypeDao;
        this.deliveryTypeEntityMapWrapper = deliveryTypeEntityMapWrapper;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.deliveryTypeDao;
    }

    @Override
    public void saveDeliveryType(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        DeliveryType deliveryType = DeliveryType.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();

        super.saveEntity(deliveryType);
        deliveryTypeEntityMapWrapper.putEntity(requestOneFieldEntityDto.getRequestValue(), deliveryType);
    }

    @Override
    public void updateDeliveryType(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        DeliveryType deliveryType = DeliveryType.builder()
                .name(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue())
                .build();

        Parameter parameter = parameterFactory.createStringParameter(
                "name",
                requestOneFieldEntityDtoUpdate.getOldEntityDto().getRequestValue());
        super.updateEntity(deliveryType, parameter);
        deliveryTypeEntityMapWrapper.putEntity(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue(),
                deliveryType);
    }

    @Override
    public void deleteDeliveryType(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter =
                parameterFactory.createStringParameter("name", requestOneFieldEntityDto.getRequestValue());

        super.deleteEntity(parameter);
        deliveryTypeEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }
}
