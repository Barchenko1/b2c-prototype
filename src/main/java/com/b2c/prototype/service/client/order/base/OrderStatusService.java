package com.b2c.prototype.service.client.order.base;

import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.client.entity.order.OrderStatus;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.service.client.order.IOrderStatusService;

import static com.b2c.prototype.util.Query.SELECT_ORDER_STATUS_BY_NAME;
import static com.b2c.prototype.util.Query.UPDATE_ORDER_STATUS_BY_NAME;

public class OrderStatusService implements IOrderStatusService {

    private final IOrderStatusDao orderStatusDao;
    private final IEntityStringMapWrapper<OrderStatus> orderStatusEntityMapWrapper;

    public OrderStatusService(IOrderStatusDao orderStatusDao,
                              IEntityStringMapWrapper<OrderStatus> orderStatusEntityMapWrapper) {
        this.orderStatusDao = orderStatusDao;
        this.orderStatusEntityMapWrapper = orderStatusEntityMapWrapper;
    }


    @Override
    public void saveOrderStatus(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        OrderStatus orderStatus = OrderStatus.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();

        orderStatusDao.saveEntity(orderStatus);
        orderStatusEntityMapWrapper.putEntity(
                requestOneFieldEntityDto.getRequestValue(),
                orderStatus);
    }

    @Override
    public void updateOrderStatus(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        RequestOneFieldEntityDto newEntityDto = requestOneFieldEntityDtoUpdate.getNewEntityDto();
        RequestOneFieldEntityDto oldEntityDto = requestOneFieldEntityDtoUpdate.getOldEntityDto();

        orderStatusDao.mutateEntityBySQLQueryWithParams(
                UPDATE_ORDER_STATUS_BY_NAME,
                newEntityDto.getRequestValue(),
                oldEntityDto.getRequestValue()
        );

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
        orderStatusDao.mutateEntityBySQLQueryWithParams(
                SELECT_ORDER_STATUS_BY_NAME,
                requestOneFieldEntityDto.getRequestValue());

        orderStatusEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }
}
