package com.b2c.prototype.manager.order.base;


import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;

public class OrderStatusManager extends AbstractConstantEntityManager<ConstantPayloadDto, OrderStatus> implements IOrderStatusManager {

    public OrderStatusManager(IParameterFactory parameterFactory,
                              ITransactionEntityDao orderStatusDao,
                              ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, orderStatusDao,
                new String[] {"OrderStatus.findByValue", "OrderStatus.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, OrderStatus.class),
                transformationFunctionService.getTransformationFunction(OrderStatus.class, ConstantPayloadDto.class)
        );
    }
}
