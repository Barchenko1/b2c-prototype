package com.b2c.prototype.service.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.dao.wrapper.IEntityStringMapWrapper;
import com.b2c.prototype.service.AbstractService;
import com.b2c.prototype.service.RequestEntityWrapper;
import com.b2c.prototype.service.delivery.IDeliveryTypeService;

import static com.b2c.prototype.util.Query.DELETE_DELIVERY_TYPE_BY_NAME;
import static com.b2c.prototype.util.Query.UPDATE_DELIVERY_TYPE_BY_NAME;

public class DeliveryTypeService extends AbstractService implements IDeliveryTypeService {

    private final IDeliveryTypeDao deliveryTypeDao;
    private final IEntityStringMapWrapper<DeliveryType> deliveryTypeEntityMapWrapper;

    public DeliveryTypeService(IDeliveryTypeDao deliveryTypeDao,
                               IEntityStringMapWrapper<DeliveryType> deliveryTypeEntityMapWrapper) {
        this.deliveryTypeDao = deliveryTypeDao;
        this.deliveryTypeEntityMapWrapper = deliveryTypeEntityMapWrapper;
    }

    @Override
    public void saveDeliveryType(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        DeliveryType deliveryType = DeliveryType.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();

        RequestEntityWrapper<DeliveryType> requestEntityWrapper = new RequestEntityWrapper<>(
                deliveryTypeDao,
                requestOneFieldEntityDto,
                deliveryType,
                deliveryTypeEntityMapWrapper
        );
        super.saveEntity(requestEntityWrapper);
    }

    @Override
    public void updateDeliveryType(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        DeliveryType deliveryType = DeliveryType.builder()
                .name(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue())
                .build();

        RequestEntityWrapper<DeliveryType> requestEntityWrapper = new RequestEntityWrapper<>(
                UPDATE_DELIVERY_TYPE_BY_NAME,
                deliveryTypeDao,
                requestOneFieldEntityDtoUpdate,
                deliveryType,
                deliveryTypeEntityMapWrapper
        );
        super.updateEntity(requestEntityWrapper);
    }

    @Override
    public void deleteDeliveryType(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        deliveryTypeDao.mutateEntityBySQLQueryWithParams(DELETE_DELIVERY_TYPE_BY_NAME,
                requestOneFieldEntityDto.getRequestValue());

        RequestEntityWrapper<DeliveryType> requestEntityWrapper = new RequestEntityWrapper<>(
                DELETE_DELIVERY_TYPE_BY_NAME,
                deliveryTypeDao,
                requestOneFieldEntityDto,
                deliveryTypeEntityMapWrapper
        );
        super.deleteEntity(requestEntityWrapper);
    }
}
