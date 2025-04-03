package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.b2c.prototype.modal.dto.payload.OrderArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderDetails;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.order.IOrderArticularItemQuantityManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;

public class OrderArticularItemQuantityManager implements IOrderArticularItemQuantityManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public OrderArticularItemQuantityManager(IOrderItemDataDao orderItemDao,
                                             ITransformationFunctionService transformationFunctionService,
                                             IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(orderItemDao);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveOrderArticularItemQuantity(OrderArticularItemQuantityDto orderArticularItemQuantityDto) {
        entityOperationManager.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption =
                    transformationFunctionService.getEntity(session, DeliveryArticularItemQuantity.class, orderArticularItemQuantityDto);
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void updateOrderArticularItemQuantity(String orderId, OrderArticularItemQuantityDto orderArticularItemQuantityDto) {
        entityOperationManager.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = entityOperationManager.getNamedQueryEntity(
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            DeliveryArticularItemQuantity newOrderItemDataOption =
                    transformationFunctionService.getEntity(DeliveryArticularItemQuantity.class, orderArticularItemQuantityDto);
            newOrderItemDataOption.setId(orderItemDataOption.getId());
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void deleteOrder(String orderId) {
        entityOperationManager.deleteEntityByParameter(
                parameterFactory.createStringParameter(ORDER_ID, orderId));
    }

    @Override
    public ResponseOrderDetails getResponseOrderDetails(String orderId) {
        return entityOperationManager.getGraphEntityDto(
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseOrderDetails.class));
    }

    @Override
    public List<ResponseOrderDetails> getResponseOrderDetailsList() {
        return entityOperationManager.getGraphEntityDtoList(
                "",
                transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseOrderDetails.class));
    }
}
