package com.b2c.prototype.service.base.order.base;

import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.service.base.order.IOrderStatusService;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

public class OrderStatusService extends AbstractSingleEntityService implements IOrderStatusService {

    private final IOrderStatusDao orderStatusDao;
    private final IEntityStringMapWrapper<OrderStatus> orderStatusEntityMapWrapper;

    public OrderStatusService(IOrderStatusDao orderStatusDao,
                              IParameterFactory parameterFactory,
                              IEntityStringMapWrapper<OrderStatus> orderStatusEntityMapWrapper) {
        this.orderStatusDao = orderStatusDao;
        this.orderStatusEntityMapWrapper = orderStatusEntityMapWrapper;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.orderStatusDao;
    }

    @Override
    public void saveOrderStatus(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        OrderStatus orderStatus = OrderStatus.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();

        super.saveEntity(orderStatus);
        orderStatusEntityMapWrapper.putEntity(
                requestOneFieldEntityDto.getRequestValue(),
                orderStatus);
    }

    @Override
    public void updateOrderStatus(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        RequestOneFieldEntityDto newEntityDto = requestOneFieldEntityDtoUpdate.getNewEntityDto();
        RequestOneFieldEntityDto oldEntityDto = requestOneFieldEntityDtoUpdate.getOldEntityDto();
        Parameter parameter = parameterFactory.createStringParameter("name", oldEntityDto.getRequestValue());

        super.updateEntity(newEntityDto, parameter);
        orderStatusEntityMapWrapper.updateEntity(
                oldEntityDto.getRequestValue(),
                newEntityDto.getRequestValue(),
                OrderStatus.builder()
                        .name(newEntityDto.getRequestValue())
                        .build()
        );
    }

    @Override
    public void deleteOrderStatus(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter =
                parameterFactory.createStringParameter("name", requestOneFieldEntityDto.getRequestValue());

        super.deleteEntity(parameter);
        orderStatusEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }
}
