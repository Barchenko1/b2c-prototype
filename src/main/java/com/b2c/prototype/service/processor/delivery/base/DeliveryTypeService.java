package com.b2c.prototype.service.processor.delivery.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.delivery.IDeliveryTypeService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class DeliveryTypeService extends AbstractConstantEntityService<DeliveryType> implements IDeliveryTypeService {

    public DeliveryTypeService(IParameterFactory parameterFactory,
                               IEntityDao deliveryTypeDao,
                               ITransformationFunctionService transformationFunctionService,
                               ISingleValueMap singleValueMap) {
        super(parameterFactory, deliveryTypeDao, transformationFunctionService, singleValueMap);
    }
}
