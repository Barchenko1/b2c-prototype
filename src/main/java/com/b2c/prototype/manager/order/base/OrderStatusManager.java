package com.b2c.prototype.manager.order.base;


import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;

public class OrderStatusManager extends AbstractConstantEntityManager<ConstantPayloadDto, OrderStatus> implements IOrderStatusManager {

    public OrderStatusManager(IParameterFactory parameterFactory,
                              IEntityDao orderStatusDao,
                              ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, orderStatusDao,
                "",
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, OrderStatus.class),
                transformationFunctionService.getTransformationFunction(OrderStatus.class, ConstantPayloadDto.class)
        );
    }
}
