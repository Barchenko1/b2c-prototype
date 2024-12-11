package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.order.IOrderStatusService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class OrderStatusService extends AbstractOneFieldEntityService<OrderStatus> implements IOrderStatusService {

    public OrderStatusService(IParameterFactory parameterFactory,
                              IEntityDao orderStatusDao,
                              IEntityCachedMap entityCachedMap) {
        super(parameterFactory, orderStatusDao, entityCachedMap);
    }

    @Override
    protected Function<OneFieldEntityDto, OrderStatus> getFunction() {
        return oneFieldEntityDto -> OrderStatus.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
