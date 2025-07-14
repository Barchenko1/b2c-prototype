package com.b2c.prototype.manager.delivery.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;

public class DeliveryTypeManager extends AbstractConstantEntityManager<ConstantPayloadDto, DeliveryType> implements IDeliveryTypeManager {

    public DeliveryTypeManager(IParameterFactory parameterFactory,
                               ITransactionEntityDao deliveryTypeDao,
                               ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory,
                deliveryTypeDao,
                new String[] {"DeliveryType.findByValue", "DeliveryType.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, DeliveryType.class),
                transformationFunctionService.getTransformationFunction(DeliveryType.class, ConstantPayloadDto.class));
    }
}
