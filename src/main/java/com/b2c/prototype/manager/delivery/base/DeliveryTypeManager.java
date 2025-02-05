package com.b2c.prototype.manager.delivery.base;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class DeliveryTypeManager extends AbstractConstantEntityManager<ConstantPayloadDto, DeliveryType> implements IDeliveryTypeManager {

    public DeliveryTypeManager(IParameterFactory parameterFactory,
                               IEntityDao deliveryTypeDao,
                               ITransformationFunctionService transformationFunctionService,
                               IConstantsScope singleValueMap) {
        super(parameterFactory, deliveryTypeDao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, DeliveryType.class),
                transformationFunctionService.getTransformationFunction(DeliveryType.class, ConstantPayloadDto.class));
    }
}
