package com.b2c.prototype.manager.delivery.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class DeliveryTypeManager extends AbstractConstantEntityManager<ConstantPayloadDto, DeliveryType> implements IDeliveryTypeManager {

    public DeliveryTypeManager(IParameterFactory parameterFactory,
                               IEntityDao deliveryTypeDao,
                               ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, deliveryTypeDao,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, DeliveryType.class),
                transformationFunctionService.getTransformationFunction(DeliveryType.class, ConstantPayloadDto.class));
    }
}
