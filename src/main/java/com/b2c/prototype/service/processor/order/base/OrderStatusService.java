package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.order.IOrderStatusService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class OrderStatusService extends AbstractOneFieldEntityService<OrderStatus> implements IOrderStatusService {

    public OrderStatusService(IParameterFactory parameterFactory,
                              IEntityDao orderStatusDao,
                              ITransformationFunctionService transformationFunctionService,
                              ISingleValueMap singleValueMap) {
        super(parameterFactory, orderStatusDao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneFieldEntityDto, OrderStatus> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, OrderStatus.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
