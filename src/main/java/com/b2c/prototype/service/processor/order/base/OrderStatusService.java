package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.order.IOrderStatusService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class OrderStatusService extends AbstractConstantEntityService<ConstantPayloadDto, OrderStatus> implements IOrderStatusService {

    public OrderStatusService(IParameterFactory parameterFactory,
                              IEntityDao orderStatusDao,
                              ITransformationFunctionService transformationFunctionService,
                              ISingleValueMap singleValueMap) {
        super(parameterFactory, orderStatusDao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, OrderStatus.class),
                transformationFunctionService.getTransformationFunction(OrderStatus.class, ConstantPayloadDto.class)
        );
    }
}
