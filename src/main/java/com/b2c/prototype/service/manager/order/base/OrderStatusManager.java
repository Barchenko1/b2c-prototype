package com.b2c.prototype.service.manager.order.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.manager.AbstractConstantEntityManager;
import com.b2c.prototype.service.manager.order.IOrderStatusManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class OrderStatusManager extends AbstractConstantEntityManager<ConstantPayloadDto, OrderStatus> implements IOrderStatusManager {

    public OrderStatusManager(IParameterFactory parameterFactory,
                              IEntityDao orderStatusDao,
                              ITransformationFunctionService transformationFunctionService,
                              ISingleValueMap singleValueMap) {
        super(parameterFactory, orderStatusDao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, OrderStatus.class),
                transformationFunctionService.getTransformationFunction(OrderStatus.class, ConstantPayloadDto.class)
        );
    }
}
