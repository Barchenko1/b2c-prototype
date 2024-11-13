package com.b2c.prototype.service.processor.delivery.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.delivery.IDeliveryTypeService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class DeliveryTypeService extends AbstractOneFieldEntityService<DeliveryType> implements IDeliveryTypeService {

    public DeliveryTypeService(IParameterFactory parameterFactory,
                               ISingleEntityDao deliveryTypeDao,
                               IEntityCachedMap entityCachedMap) {
        super(parameterFactory, deliveryTypeDao, entityCachedMap);
    }

    @Override
    protected Function<OneFieldEntityDto, DeliveryType> getFunction() {
        return requestOneFieldEntityDto -> DeliveryType.builder()
                .value(requestOneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
